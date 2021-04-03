package com.Maruszak.MantisKeeper.services;

import com.Maruszak.MantisKeeper.model.Instar;
import com.Maruszak.MantisKeeper.model.Invertebrate;
import com.Maruszak.MantisKeeper.model.User;
import com.Maruszak.MantisKeeper.repository.InvertebrateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class InvertebratesServiceImpl {

    @Autowired
    private InvertebrateRepository invertRepository;

    @Autowired
    private InstarServiceImpl instarService;

    @Autowired
    private UserDetailsServiceImpl userService;


    public Invertebrate findInvertById(long id) {
        Optional<Invertebrate> invertOptional = invertRepository.findById(id);
        if (invertOptional.isPresent()) {
            return invertOptional.get();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invertebrate not found!");
    }

    @Transactional
    public void saveInvertAndInstar(Instar instar) {
        instarService.addInstar(instar);
        Invertebrate invertebrate = instar.getInvertebrate();
        invertRepository.save(invertebrate);
    }

    @Transactional
    public void updateInvertAndInstar(List<Instar> instars) {
        instarService.deleteAllByInvert(instars.get(0).getInvertebrate());
        for (Instar instar : instars) {
            instarService.saveInstar(instar);
        }
        invertRepository.save(instars.get(0).getInvertebrate());
    }

    @Transactional
    public void deleteInvert(long id) {
        Invertebrate invert = findInvertById(id);
        instarService.deleteAllByInvert(invert);
        invertRepository.deleteById(id);
    }

    @Transactional
    public void saveAsDead(long id , LocalDate date) {
        Invertebrate invertebrate = findInvertById(id);
        invertebrate.setAlive(false);
        invertebrate.setDeath(date);
        invertRepository.save(invertebrate);
    }

    public String editInvertHTML(long id, Model model) {
        User user = userService.getUser();
        List<Invertebrate> invertebrates = invertRepository.findAllByUser(user);
        for(Invertebrate invert : invertebrates){
            invert.setInstars(instarService.findInstarsByInvertAsc(invert));
        }
        user.setInvertebratesList(invertebrates);
        Invertebrate invert = findInvertById(id);
        model.addAttribute("invert", invert);
        model.addAttribute("user", user);
        return "editInvert";
    }

    public String markInvertDeadHTML(long id, Model model) {
        model.addAttribute("id" , id);
        return "markDead";
    }

    public String deadInvertsHTML(Model model) {
        User user = userService.getUser();
        List<Invertebrate> invertebrates = invertRepository.findAllByUserAndAliveFalse(user);
        for(Invertebrate invert : invertebrates){
            invert.setInstars(instarService.findInstarsByInvertAsc(invert));
        }
        user.setInvertebratesList(invertebrates);
        model.addAttribute("user" , user);
        return "deadInverts";
    }

    public String getMyAliveInvertsHTML(Model model) {
        User user = userService.getUser();
        List<Invertebrate> invertebrates = invertRepository.findAllByUserAndAliveTrue(user);
        for(Invertebrate invert : invertebrates){
            invert.setInstars(instarService.findInstarsByInvertAsc(invert));
        }
        user.setInvertebratesList(invertebrates);
        model.addAttribute("user", user);
        return "myInverts";
    }

    public String addInvertHTML(Model model) {
        User user = userService.getUser();
        model.addAttribute("user", user);
        return "addInvert";
    }

    public String invertDetailsHTML(long id, Model model) {
        Invertebrate invert = findInvertById(id);
        model.addAttribute("invert", invert);
        return "invertDetails";
    }
}
