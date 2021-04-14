package com.Maruszak.MantisKeeper.services;

import com.Maruszak.MantisKeeper.DTO.PasswordsDTO;
import com.Maruszak.MantisKeeper.model.User;
import com.Maruszak.MantisKeeper.model.VerificationToken;
import com.Maruszak.MantisKeeper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private InvertebratesServiceImpl invertService;

    @Autowired
    private InstarServiceImpl instarService;

    @Autowired
    private VerificationTokenServiceImpl tokenService;

    @Autowired
    private EmailServiceImpl emailService;



    @Transactional
    public void register(User userTemp) {

        Optional<User> userOptional = userRepository.findByEmail(userTemp.getEmail());
        if (userOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Email address already in use");
        }
        userOptional = userRepository.findByUsername(userTemp.getUsername());
        if(userOptional.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already in use");
        }

        User user = new User();
        user.setUsername(userTemp.getUsername());
        user.setEmail(userTemp.getEmail());
        user.setPassword(passwordEncoder.encode(userTemp.getPassword()));
        user.setInvertebratesList(new ArrayList<>());
        user.setAuthority("USER");
        user.setActive(false);
        userRepository.save(user);

        VerificationToken verificationToken = new VerificationToken(user);
        tokenService.save(verificationToken);

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setSubject("Complete Registration for Inverts Keepers Website");
        mail.setFrom("invertebrates.keepers@gmail.com");
        mail.setText("To confirm your account, please click here : " +
                "http://81.97.217.199/confirmAccount?token="+verificationToken.getToken());
        emailService.sendEmail(mail);
    }

    public User getUser(){
        Object user  =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user.getClass()!=User.class){
            return null;
        }
        return (User) user;
    }


    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()){
            throw new UsernameNotFoundException(username + " not found");
        }
        return user.get();
    }

    public String profile(Model model) {
        User user = getUser();
        user.setInvertebratesList(invertService.findInvertsByUser(user));
        model.addAttribute("user" , user);
        return "userProfile";
    }

    @Transactional
    public void changePass(PasswordsDTO passwords) {
        User user = getUser();
        if(passwordEncoder.matches(passwords.getOldPass(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(passwords.getNewPass()));
            userRepository.save(user);
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Old Password!");
        }
    }

    @Transactional
    public String activateUser(String token, Model model) {
        Optional<VerificationToken> tokenOptional = tokenService.findByToken(token);
        if(tokenOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad link or token doesn't exist!");
        }else {
            User user = tokenOptional.get().getUser();
            user.setActive(true);
            userRepository.save(user);
            tokenService.deleteToken(tokenOptional.get().getId());
            model.addAttribute("message" , "User account successfully activated");
        }
        return "activation";
    }
}
