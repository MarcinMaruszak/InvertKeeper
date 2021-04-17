package com.Maruszak.MantisKeeper.services;

import com.Maruszak.MantisKeeper.DTO.ContactDTO;
import com.Maruszak.MantisKeeper.model.Invertebrate;
import com.Maruszak.MantisKeeper.model.Photo;
import com.Maruszak.MantisKeeper.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WebServiceImpl {

    @Autowired
    private UserDetailsServiceImpl userService;

    @Autowired
    private InvertebratesServiceImpl invertService;

    @Autowired
    private InstarServiceImpl instarService;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private PhotoServiceImpl photoService;

    public String getHomeHTML(Model model) {
        User user = userService.getUser();
        List<Invertebrate> inverts = invertService.findLast10Added();
        List<Photo> photos = new ArrayList<>();
        for(Invertebrate invert : inverts){
            invert.setInstars(instarService.findInstarsByInvertAsc(invert));
            Optional<Photo> photoOptional = photoService.findLatestByInvert(invert);
            photoOptional.ifPresent(photos::add);
        }
        model.addAttribute("user" , user);
        model.addAttribute("inverts" , inverts);
        model.addAttribute("photos" , photos);
        return "home";
    }

    public String register() {
        return "register";
    }

    public String login() {
        return "login";
    }

    public void contact(ContactDTO contactDTO) {
        User user = userService.getUser();
        if(user==null){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not Authenticated");
        }
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(contactDTO.getType());
        email.setTo("invertebrates.keepers@gmail.com");
        email.setText(user.getEmail()+"  " +user.getUsername() +"\n\n" +contactDTO.getMessage());

        emailService.sendEmail(email);
    }

    public String contactHTML() {
        return "contact";
    }
}
