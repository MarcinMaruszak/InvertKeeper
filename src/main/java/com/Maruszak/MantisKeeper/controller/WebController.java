package com.Maruszak.MantisKeeper.controller;

import com.Maruszak.MantisKeeper.services.WebServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @Autowired
    WebServiceImpl webService;

    @GetMapping(path = "/")
    public String home(){
        return webService.getHome();
    }

    @GetMapping(path = "/myPets")
    public String myPets(Model model){
        return webService.getMyPets(model);
    }
}
