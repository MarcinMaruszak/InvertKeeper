package com.Maruszak.MantisKeeper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping(path = "/")
    public String home(){
        return "home";
    }

    @GetMapping(path = "/myPets")
    public String myPets(){
        return "myPets";
    }
}
