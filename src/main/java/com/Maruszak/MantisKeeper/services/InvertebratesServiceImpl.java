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
    private InvertebrateRepository invRepository;

    @Autowired
    private InstarServiceImpl instarService;

    @Autowired
    private UserDetailsServiceImpl userService;

    public List<Invertebrate> getUserInvertebrates(User user){
        return invRepository.findAllByUser(user);
    }

    public void addPet(Invertebrate invertebrate) {
        invRepository.save(invertebrate);
    }
}
