package com.Maruszak.MantisKeeper.controller;

import com.Maruszak.MantisKeeper.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @Autowired
    UserDetailsServiceImpl userServices;

    @GetMapping(path = "/register")
    public String register(){
        return "register";
    }
}
