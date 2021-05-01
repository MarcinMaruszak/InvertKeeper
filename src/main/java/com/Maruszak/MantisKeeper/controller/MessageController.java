package com.Maruszak.MantisKeeper.controller;

import com.Maruszak.MantisKeeper.DTO.MessageDTO;
import com.Maruszak.MantisKeeper.services.MessageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class MessageController {

    @Autowired
    private MessageServiceImpl messageService;

    @GetMapping(path = "/message/new")
    public String newMessage(@RequestParam(value = "id") UUID id,
                             @RequestParam(value = "subject", required = false, defaultValue = "") String subject,
                             Model model){
        return messageService.newMessage(model, id, subject);
    }

    @PostMapping(path = "/message/send")
    public @ResponseBody
    void sendNewMessage(@RequestBody MessageDTO messageDTO){
        messageService.sendNewMessage(messageDTO);
    }

    @GetMapping(path = "/message/inbox")
    public String myMessages(Model model){
        return messageService.myMessages(model);
    }

    @GetMapping(path = "/message/sent")
    public String sentMessages(Model model){
        return messageService.sentMessages(model);
    }

    @GetMapping(path = "/message/details/{id}")
    public String messageDetail(Model model, @PathVariable UUID id){
        return messageService.messageDetails(id, model);
    }
}
