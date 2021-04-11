package com.Maruszak.MantisKeeper.controller;

import com.Maruszak.MantisKeeper.DTO.PasswordsDTO;
import com.Maruszak.MantisKeeper.model.User;
import com.Maruszak.MantisKeeper.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    @Lazy
    UserDetailsServiceImpl userServices;


    @PostMapping(path = "/api/register")
    public @ResponseBody
    User register(@Valid @RequestBody User user) {
        return userServices.register(user);
    }

    @GetMapping(path = "/profile")
    public String profile(Model model) {
        return userServices.profile(model);
    }

    @PostMapping(path = "/api/changePass")
    public @ResponseBody void changePass(@Valid @RequestBody PasswordsDTO passwords){
        userServices.changePass(passwords);
    }
}




