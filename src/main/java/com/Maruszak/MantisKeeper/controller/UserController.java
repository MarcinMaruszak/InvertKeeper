package com.Maruszak.MantisKeeper.controller;

import com.Maruszak.MantisKeeper.model.User;
import com.Maruszak.MantisKeeper.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    //---HTML--

    @Autowired
    UserDetailsServiceImpl userServices;


    @GetMapping(path = "/register")
    public String register(){
        return "register";
    }



    //-----API---

    @PostMapping(path = "/api/register")
    public  @ResponseBody
    User register(@RequestBody User user){
        return userServices.register(user);
    }

}
