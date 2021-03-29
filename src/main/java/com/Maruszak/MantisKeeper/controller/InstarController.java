package com.Maruszak.MantisKeeper.controller;

import com.Maruszak.MantisKeeper.services.InstarServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class InstarController {

    @Autowired
    InstarServiceImpl instarService;


}
