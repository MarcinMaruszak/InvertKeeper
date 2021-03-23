package com.Maruszak.MantisKeeper.services;

import com.Maruszak.MantisKeeper.model.User;
import com.Maruszak.MantisKeeper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserServices{

    @Autowired
    UserRepository userRepository;

    @Override
    public User register(User user) {
        return user;
    }
}
