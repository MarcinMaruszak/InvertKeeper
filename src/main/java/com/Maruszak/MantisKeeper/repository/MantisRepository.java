package com.Maruszak.MantisKeeper.repository;

import com.Maruszak.MantisKeeper.model.Mantis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MantisRepository extends JpaRepository<Mantis, Long> {

    Page<Mantis> findAll(Specification<Mantis> specification, Pageable pageable);
}
