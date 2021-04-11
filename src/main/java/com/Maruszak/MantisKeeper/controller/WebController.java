package com.Maruszak.MantisKeeper.controller;

import com.Maruszak.MantisKeeper.services.WebServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @Autowired
    private WebServiceImpl webService;

    @GetMapping(path = "/")
    public String home(Model model){
        return webService.getHomeHTML(model);
    }

    @GetMapping(path = "/register")
    public String register() {
        return webService.register();
    }

    @GetMapping(path = "/login")
    public String login(){
        return webService.login();
    }
}
