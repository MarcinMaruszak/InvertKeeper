package com.Maruszak.MantisKeeper.services;

import com.Maruszak.MantisKeeper.model.Mantis;
import com.Maruszak.MantisKeeper.repository.MantisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class MantisServiceImpl {

    @Autowired
    private MantisRepository mantisRepository;

    Page<Mantis> findAll(Specification<Mantis> specification, Pageable pageable){
        return mantisRepository.findAll(specification, pageable);
    }
}
