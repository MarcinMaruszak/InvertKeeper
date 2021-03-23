package com.Maruszak.MantisKeeper.services;

import com.Maruszak.MantisKeeper.model.User;
import com.Maruszak.MantisKeeper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserServices{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User register(User userTemp) {

        Optional<User> userOptional = userRepository.findByEmail(userTemp.getEmail());
        if(userOptional.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST ,
                    "Email address already in use");
        }
        User user = new User();
        user.setEmail(userTemp.getEmail());
        user.setPassword(passwordEncoder.encode(userTemp.getPassword()));
        user.setInvertebratesList(new ArrayList<>());
        user.setRole(new GrantedAuthority[]{new SimpleGrantedAuthority("USER")});
        userRepository.save(user);
        return user;
    }
}
