package com.Maruszak.MantisKeeper.controller;

import com.Maruszak.MantisKeeper.model.Message;
import com.Maruszak.MantisKeeper.model.User;
import com.Maruszak.MantisKeeper.services.MessageServiceImpl;
import com.Maruszak.MantisKeeper.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class NewMessageControllerAdvice {

    @Autowired
    MessageServiceImpl messageService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @ModelAttribute
    public void newMessage(Model model){
        User user = userDetailsService.getUser();
        List<Message> messages = null;
        if(user!=null){
            messages=messageService.findAllByReceiverAndUnread(user);
        }
        model.addAttribute("newMessages" , messages);
    }
}
