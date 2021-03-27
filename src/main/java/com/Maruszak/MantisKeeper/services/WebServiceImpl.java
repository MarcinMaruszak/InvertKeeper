package com.Maruszak.MantisKeeper.services;

import com.Maruszak.MantisKeeper.model.Invertebrate;
import com.Maruszak.MantisKeeper.model.User;
import com.Maruszak.MantisKeeper.repository.InstarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class WebServiceImpl {

    @Autowired
    InvertebratesServiceImpl invService;

    @Autowired
    InstarRepository instarRepository;

    public String getHome() {
        return "home";
    }

    public String getMyPets(Model model) {
        User user  = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Invertebrate> invertebrates = invService.getUserInvertebrates(user);
        for(Invertebrate invert : invertebrates){
            invert.setInstars(instarRepository.findAllByInvertebrateOrderByDateAsc(invert));
        }
        user.setInvertebratesList(invertebrates);
        model.addAttribute("user" , user);
        System.out.println(user);
        return "myPets";
    }
}
