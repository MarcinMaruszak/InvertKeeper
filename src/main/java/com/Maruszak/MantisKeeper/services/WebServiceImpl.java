package com.Maruszak.MantisKeeper.services;

import com.Maruszak.MantisKeeper.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class WebServiceImpl {

    @Autowired
    private UserDetailsServiceImpl userService;

    @Autowired
    private InvertebratesServiceImpl invService;

    @Autowired
    private InstarServiceImpl instarService;

    public String getHome(Model model) {
        User user = userService.getUser();
        model.addAttribute("user" , user);
        return "home";
    }

    public String getMyPets(Model model) {
        User user = userService.getUser();
        model.addAttribute("user", user);
        return "myInverts";
    }

    public String addPet(Model model) {
        User user = userService.getUser();
        model.addAttribute("user", user);
        return "addInvert";
    }
}
