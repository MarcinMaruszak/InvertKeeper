package com.Maruszak.MantisKeeper.services;

import com.Maruszak.MantisKeeper.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserServices {

    public User register(User user);
}
