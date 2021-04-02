package com.Maruszak.MantisKeeper.controller;

import com.Maruszak.MantisKeeper.model.Instar;
import com.Maruszak.MantisKeeper.services.InvertebratesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class InvertebrateController {

    @Autowired
    InvertebratesServiceImpl invertService;


    @GetMapping(path = "/myInverts/edit/{id}")
    public String editInvert(@PathVariable long id, Model model) {
        return invertService.editInvert(id, model);
    }

    @Transactional
    @PostMapping(path = "/api/addInvert")
    public @ResponseBody
    void addInvert(@Valid @RequestBody Instar instar){
        invertService.addInvert(instar);
    }

    @Transactional
    @PostMapping(path = "/api/updateInvert")
    public @ResponseBody
    void updateInvert(@RequestBody List<Instar> instars){
        invertService.updateInvert(instars);
    }
}
