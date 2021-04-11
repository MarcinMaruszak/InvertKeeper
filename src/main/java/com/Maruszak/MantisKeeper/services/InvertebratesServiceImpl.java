package com.Maruszak.MantisKeeper.services;

import com.Maruszak.MantisKeeper.DTO.InvertDTO;
import com.Maruszak.MantisKeeper.model.Instar;
import com.Maruszak.MantisKeeper.model.Invertebrate;
import com.Maruszak.MantisKeeper.model.User;
import com.Maruszak.MantisKeeper.repository.InvertebrateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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


    public Invertebrate findInvertById(UUID id) {
        Optional<Invertebrate> invertOptional = invertRepository.findById(id);
        if (invertOptional.isPresent()) {
            return invertOptional.get();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invertebrate not found!");
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
        if(!invert.getUser().getId().equals(user.getId())){
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
        if(!invert.getUser().getId().equals(user.getId())){
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
        if(!invert.getUser().getId().equals(user.getId())){
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
        if(!invert.getUser().getId().equals(user.getId())){
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
        model.addAttribute("userId" , userService.getUser().getId());
        model.addAttribute("invert", invert);
        return "invertDetails";
    }

    public List<Invertebrate> findLast10Added() {
        return invertRepository.findTop10ByOrderByAddedDesc();
    }
}
