package com.Maruszak.MantisKeeper.controller;

import com.Maruszak.MantisKeeper.DTO.ContactDTO;
import com.Maruszak.MantisKeeper.services.WebServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
public class WebController {

    @Autowired
    private WebServiceImpl webService;

    @GetMapping(path = "/")
    public String home(Model model) {
        return webService.getHomeHTML(model);
    }

    @GetMapping(path = "/register")
    public String register() {
        return webService.register();
    }

    @GetMapping(path = "/login")
    public String login() {
        return webService.login();
    }

    @GetMapping(path = "/contact")
    public String contactHTML(){
        return webService.contactHTML();
    }

    @PostMapping(path = "/contact")
    public @ResponseBody
    void contact(@Valid @RequestBody ContactDTO contactDTO){
        webService.contact(contactDTO);
    }
}
