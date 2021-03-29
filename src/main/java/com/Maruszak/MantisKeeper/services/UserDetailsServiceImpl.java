package com.Maruszak.MantisKeeper.services;

import com.Maruszak.MantisKeeper.model.Invertebrate;
import com.Maruszak.MantisKeeper.model.User;
import com.Maruszak.MantisKeeper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
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


    public User register(User userTemp) {

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
        user.setActive(true);
        userRepository.save(user);
        return user;
    }

    public User getUser(){
        Object user  =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user.getClass()!=User.class){
            return null;
        }
        List<Invertebrate> invertebrates = invertService.getUserInvertebrates((User) user);
        for(Invertebrate invert : invertebrates){
            invert.setInstars(instarService.getInstarsByInvertAsc(invert));
        }
        ((User)user).setInvertebratesList(invertebrates);
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
}
