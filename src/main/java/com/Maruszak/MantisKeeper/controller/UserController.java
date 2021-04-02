package com.Maruszak.MantisKeeper.controller;

import com.Maruszak.MantisKeeper.model.User;
import com.Maruszak.MantisKeeper.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    @Lazy
    UserDetailsServiceImpl userServices;


    @GetMapping(path = "/register")
    public String register() {
        return "register";
    }


    @PostMapping(path = "/api/register")
    public @ResponseBody
    User register(@Valid @RequestBody User user) {
        return userServices.register(user);
    }

}
