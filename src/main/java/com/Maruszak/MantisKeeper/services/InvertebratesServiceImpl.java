package com.Maruszak.MantisKeeper.services;

import com.Maruszak.MantisKeeper.model.Invertebrate;
import com.Maruszak.MantisKeeper.model.User;
import com.Maruszak.MantisKeeper.repository.InvertebrateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvertebratesServiceImpl{

    @Autowired
    InvertebrateRepository invRepository;

    public List<Invertebrate> getUserInvertebrates(User user){
        return invRepository.findAllByUser(user);
    }
}
