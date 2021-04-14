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
    private UserDetailsServiceImpl userServices;


    @PostMapping(path = "/register")
    public @ResponseBody
    void register(@Valid @RequestBody User user) {
        userServices.register(user);
    }

    @GetMapping(path = "/profile")
    public String profile(Model model) {
        return userServices.profile(model);
    }

    @PostMapping(path = "/changePass")
    public @ResponseBody void changePass(@Valid @RequestBody PasswordsDTO passwords){
        userServices.changePass(passwords);
    }

    @RequestMapping(path = "/confirmAccount")
    public String confirmAccount(@RequestParam("token") String token, Model model){
        return userServices.activateUser(token, model);
    }
}




