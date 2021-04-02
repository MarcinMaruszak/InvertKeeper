package com.Maruszak.MantisKeeper.controller;

import com.Maruszak.MantisKeeper.model.Invertebrate;
import com.Maruszak.MantisKeeper.model.User;
import com.Maruszak.MantisKeeper.services.InvertebratesServiceImpl;
import com.Maruszak.MantisKeeper.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class InvertebrateController {

    @Autowired
    InvertebratesServiceImpl invertebratesService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @GetMapping(path = "/myInverts/edit/{id}")
    public String editInvert(@PathVariable long id, Model model) {
        User user = userDetailsService.getUser();
        Invertebrate invert = invertebratesService.findInvertById(id);
        model.addAttribute("invert", invert);
        model.addAttribute("user", user);
        return "editInvert";
    }
}
