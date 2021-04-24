package com.Maruszak.MantisKeeper.services;

import com.Maruszak.MantisKeeper.DTO.InvertDTO;
import com.Maruszak.MantisKeeper.DTO.TableDTO;
import com.Maruszak.MantisKeeper.model.*;
import com.Maruszak.MantisKeeper.repository.InvertebrateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class InvertebratesServiceImpl {

    @Autowired
    private InvertebrateRepository invertRepository;

    @Autowired
    private InstarServiceImpl instarService;

    @Autowired
    private UserDetailsServiceImpl userService;

    @Autowired
    private PhotoServiceImpl photoService;

    @Autowired
    private MantisServiceImpl mantisService;

    @Autowired
    private SpiderServiceImpl spiderService;


    public List<Invertebrate> findInvertsByUser(User user) {
        return invertRepository.findAllByUser(user);
    }

    public Invertebrate findInvertById(UUID id) {
        Optional<Invertebrate> invertOptional = invertRepository.findById(id);
        if (invertOptional.isPresent()) {
            return invertOptional.get();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invertebrate not found!");
    }

    public void deleteAll(List<Invertebrate> invertebrates) {
        invertRepository.deleteAll(invertebrates);
    }

    @Transactional
    public void saveNewInvert(InvertDTO invertDTO, List<MultipartFile> photos, MultipartFile avatar) {
        invertDTO.getInvertebrate().setUser(userService.getUser());
        for (Instar instar : invertDTO.getInstars()) {
            instar.setInvertebrate(invertDTO.getInvertebrate());
        }
        instarService.saveAllInstars(invertDTO.getInstars());
        invertDTO.getInvertebrate().setAdded(LocalDateTime.now());
        invertRepository.save(invertDTO.getInvertebrate());
        photoService.saveFiles(photos, invertDTO.getInvertebrate());
        photoService.saveAvatar(avatar, invertDTO.getInvertebrate());
    }

    @Transactional
    public void updateInvert(InvertDTO invertDTO, List<MultipartFile> photos, List<UUID> ids,
                             UUID avatarId) {
        User user = userService.getUser();
        invertDTO.getInvertebrate().setUser(user);
        for (Instar instar : invertDTO.getInstars()) {
            instar.setInvertebrate(invertDTO.getInvertebrate());
        }
        instarService.deleteAllByInvert(invertDTO.getInvertebrate());
        instarService.saveAllInstars(invertDTO.getInstars());
        invertRepository.save(invertDTO.getInvertebrate());
        photoService.deleteAllById(ids);
        photoService.saveFiles(photos, invertDTO.getInvertebrate());
        photoService.saveAsAvatar(avatarId, invertDTO.getInvertebrate());
    }

    @Transactional
    public void deleteInvert(UUID id) {
        User user = userService.getUser();
        Invertebrate invert = findInvertById(id);
        if (!invert.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Not Your invert cannot delete");
        }
        instarService.deleteAllByInvert(invert);
        photoService.deleteAllByInvert(invert);
        invertRepository.deleteById(id);
    }

    @Transactional
    public void saveAsDead(UUID id, LocalDate date) {
        User user = userService.getUser();
        Invertebrate invert = findInvertById(id);
        if (!invert.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Not Your invert, cannot change it");
        }
        invert.setAlive(false);
        invert.setDeath(date);
        invertRepository.save(invert);
    }

    public String editInvertHTML(UUID id, Model model) {
        User user = userService.getUser();
        Invertebrate invert = findInvertById(id);
        if (!invert.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Not Your invert, cannot edit");
        }
        invert.setUser(user);
        invert.setInstars(instarService.findInstarsByInvertAsc(invert));
        invert.setPhotos(photoService.findAllByInvert(invert));
        model.addAttribute("invert", invert);
        return "editInvert";
    }

    public String markInvertDeadHTML(UUID id, Model model) {
        User user = userService.getUser();
        Invertebrate invert = findInvertById(id);
        if (!invert.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Not Your invert, cannot change it");
        }
        model.addAttribute("id", id);
        return "markDead";
    }

    public String deadInvertsHTML(Model model) {
        User user = userService.getUser();
        List<Invertebrate> invertebrates = invertRepository.findAllByUserAndAliveFalse(user);
        for (Invertebrate invert : invertebrates) {
            invert.setInstars(instarService.findInstarsByInvertAsc(invert));
        }
        user.setInvertebratesList(invertebrates);
        model.addAttribute("user", user);
        return "deadInverts";
    }

    public String getMyAliveInvertsHTML(Model model) {
        User user = userService.getUser();
        List<Invertebrate> invertebrates = invertRepository.findAllByUserAndAliveTrue(user);
        for (Invertebrate invert : invertebrates) {
            invert.setInstars(instarService.findInstarsByInvertAsc(invert));
        }
        user.setInvertebratesList(invertebrates);
        model.addAttribute("user", user);
        return "myInverts";
    }

    public String addInvertHTML(Model model) {
        User user = userService.getUser();
        List<Invertebrate> invertebrates = invertRepository.findAllByUser(user);
        user.setInvertebratesList(invertebrates);
        model.addAttribute("user", user);
        return "addInvert";
    }

    public String invertDetailsHTML(UUID id, Model model) {
        Invertebrate invert = findInvertById(id);
        invert.setPhotos(photoService.findAllFromGalleryByInvert(invert));
        invert.setInstars(instarService.findInstarsByInvertAsc(invert));
        invert.setAvatar(photoService.findAvatar(invert));
        model.addAttribute("userId", userService.getUser().getId());
        model.addAttribute("invert", invert);
        return "invertDetails";
    }

    public List<Invertebrate> findLast10Added() {
        return invertRepository.findTop10ByOrderByAddedDesc();
    }

    public String allInvertsHTML(Model model, String sortBy, int pageNo, String direction, int pageSize) {

        Sort sort = direction.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Invertebrate> invertsPage = invertRepository.findAll(pageable);
        List<Invertebrate> inverts = invertsPage.getContent();
        for (Invertebrate invert : inverts) {
            invert.setInstars(instarService.findInstarsByInvertAsc(invert));
        }

        TableDTO tableDTO = new TableDTO();
        tableDTO.setInverts(inverts);
        tableDTO.setPageNo(pageNo);
        tableDTO.setSortBY(sortBy);
        tableDTO.setDirection(direction);
        tableDTO.setTotalPages(invertsPage.getTotalPages());
        model.addAttribute("tableDTO", tableDTO);
        return "AllInverts";
    }

    public String allInvertsSort(Model model, String sortBy, int pageNo, String direction, int pageSize,
                                 Type insectType, Sex sex, L lastInstar, String bornAfter, String bornBefore,
                                 String addedAfter, String addedBefore, String specie) {

        Sort sort = direction.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Specification<? extends Invertebrate> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            if (insectType != null) {
                predicateList.add(criteriaBuilder.equal(root.get("insectType"), insectType));
                if (specie != null) {
                    Specie specieTemp = null;
                    if (insectType.equals(Type.Mantis)) {
                        specieTemp = MantisSpecie.valueOf(specie);
                    } else if (insectType.equals(Type.Spider)) {
                        specieTemp = SpiderSpecie.valueOf(specie);
                    }
                    predicateList.add(criteriaBuilder.equal(root.get("specie"), specieTemp));
                }
            }
            if (sex != null) {
                predicateList.add(criteriaBuilder.equal(root.get("sex"), sex));
            }
            if (lastInstar != null) {
                predicateList.add(criteriaBuilder.equal(root.get("lastInstar"), lastInstar));
            }

            LocalDate bornBeforeLocalDate = LocalDate.now();
            LocalDate bornAfterLocalDate = LocalDate.MIN;
            if (bornBefore != null) {
                bornBeforeLocalDate = LocalDate.parse(bornBefore);
            }
            if (bornAfter != null) {
                bornAfterLocalDate = LocalDate.parse(bornAfter);
            }
            predicateList.add(criteriaBuilder.between(root.get("birth"), bornAfterLocalDate,
                    bornBeforeLocalDate));

            LocalDateTime addedBeforeLocalDate = LocalDateTime.now();
            LocalDateTime addedAfterLocalDate = LocalDateTime.MIN;
            if (addedBefore != null) {
                addedBeforeLocalDate = LocalDateTime.parse(addedBefore + "T23:59:59");
            }
            if (addedAfter != null) {
                addedAfterLocalDate = LocalDateTime.parse(addedAfter + "T00:00:00");
            }
            predicateList.add(criteriaBuilder.between(root.get("added"), addedAfterLocalDate,
                    addedBeforeLocalDate));

            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        };
        Page<? extends Invertebrate> invertsPage = null;
        if (insectType != null) {
            if (insectType.equals(Type.Mantis)) {
                Specification<Mantis> mantisSpecification = (Specification<Mantis>) specification;
                invertsPage = mantisService.findAll(mantisSpecification, pageable);
            } else if (insectType.equals(Type.Spider)) {
                Specification<Spider> spiderSpecification = (Specification<Spider>) specification;
                invertsPage = spiderService.findAll(spiderSpecification, pageable);
            }
        } else {
            Specification<Invertebrate> invertebrateSpecification = (Specification<Invertebrate>) specification;
            invertsPage = invertRepository.findAll(invertebrateSpecification, pageable);
        }

        List<Invertebrate> inverts = (List<Invertebrate>) invertsPage.getContent();
        for (Invertebrate invert : inverts) {
            invert.setInstars(instarService.findInstarsByInvertAsc(invert));
        }

        TableDTO tableDTO = new TableDTO();
        tableDTO.setInverts(inverts);
        tableDTO.setPageNo(pageNo);
        tableDTO.setTotalPages(invertsPage.getTotalPages());
        tableDTO.setSortBY(sortBy);
        tableDTO.setDirection(direction);
        tableDTO.setInsectType(insectType);
        tableDTO.setSex(sex);
        tableDTO.setLastInstar(lastInstar);
        model.addAttribute("tableDTO", tableDTO);
        return "AllInverts::table";
    }

    public Map<Specie, String> getSpecie(String type) {
        Map<Specie, String> speciesMap = new LinkedHashMap<>();
        if (type.equals("Mantis")) {
            Specie[] species = MantisSpecie.values();
            Arrays.stream(species).forEach(specie -> speciesMap.put(specie, specie.getFullName()));
        } else if (type.equals("Spider")) {
            Specie[] species = SpiderSpecie.values();
            Arrays.stream(species).forEach(specie -> speciesMap.put(specie, specie.getFullName()));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Type not found");
        }
        return speciesMap;
    }
}
