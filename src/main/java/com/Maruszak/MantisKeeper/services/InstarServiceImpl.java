package com.Maruszak.MantisKeeper.services;

import com.Maruszak.MantisKeeper.model.Instar;
import com.Maruszak.MantisKeeper.model.Invertebrate;
import com.Maruszak.MantisKeeper.repository.InstarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class InstarServiceImpl {

    @Autowired
    InstarRepository instarRepository;

    public List<Instar> findInstarsByInvertAsc(Invertebrate invertebrate){
        return instarRepository.findAllByInvertebrateOrderByMoltDateAsc(invertebrate);
    }

    public void addInstar(Instar instar){
        instarRepository.save(instar);
    }

    public Instar findInstarByID(long id){
        Optional<Instar> instarOptional = instarRepository.findById(id);
        if(instarOptional.isPresent()){
            return instarOptional.get();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Instar doesn't exist!");
    }

    public Instar saveInstar(Instar instar){
        return instarRepository.save(instar);
    }

    public void deleteAllByInvert(Invertebrate invertebrate){
        instarRepository.deleteAllByInvertebrate(invertebrate);
    }

    public void saveAllInstars(List<Instar> instars){
        instarRepository.saveAll(instars);
    }

}

