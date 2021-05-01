package com.Maruszak.MantisKeeper.services;

import com.Maruszak.MantisKeeper.DTO.MessageDTO;
import com.Maruszak.MantisKeeper.model.Message;
import com.Maruszak.MantisKeeper.model.User;
import com.Maruszak.MantisKeeper.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MessageServiceImpl {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public String newMessage(Model model, UUID id, String subject) {
        Optional<User> receiver = userDetailsService.findUserByID(id);

        if (receiver.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!");
        }
        User user = userDetailsService.getUser();
        if(receiver.get().getId().equals(user.getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sending message to yourself not allowed!");
        }
        model.addAttribute("subject", subject);
        model.addAttribute("receiverName", receiver.get().getUsername());
        model.addAttribute("receiverId", receiver.get().getId());
        return "newMessage";
    }

    public void sendNewMessage(MessageDTO messageDTO) {

        Message message = new Message();
        message.setSubject(messageDTO.getSubject());
        message.setMessage(messageDTO.getMessage());
        message.setSeen(false);
        message.setSentDateTime(LocalDateTime.now());
        User sender = userDetailsService.getUser();
        message.setSender(sender);
        Optional<User> receiverOpt = userDetailsService.findUserByID(messageDTO.getReceiver());
        if (receiverOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Receiver not found");
        }
        message.setReceiver(receiverOpt.get());
        messageRepository.save(message);
    }

    public String myMessages(Model model) {
        User user = userDetailsService.getUser();
        List<Message> messages = messageRepository.findAllByReceiverOrderBySentDateTimeDesc(user);
        model.addAttribute("messages", messages);
        return "inbox";
    }

    @Transactional
    public String messageDetails(UUID id, Model model) {
        User user = userDetailsService.getUser();
        Optional<Message> messageOpt = messageRepository.findById(id);

        if (messageOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't find message");
        }
        Message message = messageOpt.get();
        if (!user.getId().equals(message.getReceiver().getId())&&!user.getId().equals(message.getSender().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not your message! Cannot read it");
        }
        if(user.getId().equals(message.getReceiver().getId())){
            message.setSeen(true);
            messageRepository.save(message);
        }
        model.addAttribute("message", message);
        model.addAttribute("userId" , user.getId());
        return "message";
    }

    public List<Message> findAllByReceiverAndUnread(User user) {
        return messageRepository.findAllByReceiverAndSeenFalse(user);
    }

    public String sentMessages(Model model) {
        User user = userDetailsService.getUser();
        List<Message> messages = messageRepository.findAllBySenderOrderBySentDateTimeDesc(user);
        model.addAttribute("messages" , messages);
        return "sentMessages";
    }
}
