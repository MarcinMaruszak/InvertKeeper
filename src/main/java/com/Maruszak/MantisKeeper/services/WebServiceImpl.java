package com.Maruszak.MantisKeeper.services;

import com.Maruszak.MantisKeeper.model.Invertebrate;
import com.Maruszak.MantisKeeper.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class WebServiceImpl {

    @Autowired
    private UserDetailsServiceImpl userService;

    @Autowired
    private InvertebratesServiceImpl invertService;

    @Autowired
    private InstarServiceImpl instarService;

    public String getHomeHTML(Model model) {
        User user = userService.getUser();
        List<Invertebrate> inverts = invertService.findLast10Added();
        for(Invertebrate invert : inverts){
            invert.setInstars(instarService.findInstarsByInvertAsc(invert));
        }
        model.addAttribute("user" , user);
        model.addAttribute("inverts" , inverts);
        return "home";
    }

    public String register() {
        return "register";
    }

    public String login() {
        return "login";
    }
}
