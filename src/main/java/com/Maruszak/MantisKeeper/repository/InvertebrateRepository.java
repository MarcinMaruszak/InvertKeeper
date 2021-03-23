package com.Maruszak.MantisKeeper.repository;

import com.Maruszak.MantisKeeper.model.Invertebrate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvertebrateRepository extends JpaRepository<Invertebrate , Long> {
}
