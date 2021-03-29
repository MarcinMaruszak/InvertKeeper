package com.Maruszak.MantisKeeper.controller;

import com.Maruszak.MantisKeeper.model.Instar;
import com.Maruszak.MantisKeeper.model.Invertebrate;
import com.Maruszak.MantisKeeper.services.InstarServiceImpl;
import com.Maruszak.MantisKeeper.services.InvertebratesServiceImpl;
import com.Maruszak.MantisKeeper.services.WebServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private InstarServiceImpl instarService;

    @Autowired
    private InvertebratesServiceImpl invertService;

    @GetMapping(path = "/")
    public String home(Model model){
        return webService.getHome(model);
    }

    @GetMapping(path = "/myInverts")
    public String myPets(Model model){
        return webService.getMyPets(model);
    }

    @GetMapping(path = "/myInverts/addInvert")
    public String addNew(Model model){return webService.addPet(model);}

    @Transactional
    @PostMapping(path = "/api/addInvert")
    public @ResponseBody
    void addInstar(@Valid @RequestBody Instar instar){
        instarService.addInstar(instar);
        Invertebrate invertebrate =  instar.getInvertebrate();
        invertService.addPet(invertebrate);
    }
}
