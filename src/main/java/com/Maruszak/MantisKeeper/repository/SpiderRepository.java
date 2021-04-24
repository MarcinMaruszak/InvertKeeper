package com.Maruszak.MantisKeeper.repository;

import com.Maruszak.MantisKeeper.model.Spider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpiderRepository extends JpaRepository<Spider, Long> {

    Page<Spider> findAll(Specification<Spider> specification, Pageable pageable);
}
