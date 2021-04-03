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
    private InvertebratesServiceImpl invertService;

    @Autowired
    private InstarServiceImpl instarService;

    public String getHomeHTML(Model model) {
        User user = userService.getUser();
        model.addAttribute("user" , user);
        return "home";
    }
}
