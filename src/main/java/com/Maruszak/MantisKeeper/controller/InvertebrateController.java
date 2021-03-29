package com.Maruszak.MantisKeeper.controller;

import com.Maruszak.MantisKeeper.services.InvertebratesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class InvertebrateController {

    @Autowired
    InvertebratesServiceImpl invertebratesService;

}
