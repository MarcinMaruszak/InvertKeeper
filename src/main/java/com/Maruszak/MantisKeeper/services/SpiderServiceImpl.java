package com.Maruszak.MantisKeeper.services;

import com.Maruszak.MantisKeeper.model.Spider;
import com.Maruszak.MantisKeeper.repository.SpiderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SpiderServiceImpl {

    @Autowired
    private SpiderRepository spiderRepository;

    Page<Spider> findAll(Specification<Spider> specification, Pageable pageable){
        return spiderRepository.findAll(specification, pageable);
    }
}
