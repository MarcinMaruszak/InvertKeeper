package com.Maruszak.MantisKeeper.services;

import com.Maruszak.MantisKeeper.model.Instar;
import com.Maruszak.MantisKeeper.model.Invertebrate;
import com.Maruszak.MantisKeeper.repository.InstarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstarServiceImpl {

    @Autowired
    InstarRepository instarRepository;

    public List<Instar> getInstarsByInvertAsc(Invertebrate invertebrate){
        return instarRepository.findAllByInvertebrateOrderByDateAsc(invertebrate);
    }

    public void addInstar(Instar instar){
        instarRepository.save(instar);
    }
}
