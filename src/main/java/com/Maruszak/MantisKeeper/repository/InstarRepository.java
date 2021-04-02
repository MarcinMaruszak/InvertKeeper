package com.Maruszak.MantisKeeper.repository;

import com.Maruszak.MantisKeeper.model.Instar;
import com.Maruszak.MantisKeeper.model.Invertebrate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstarRepository extends JpaRepository<Instar, Long> {

    public List<Instar> findAllByInvertebrateOrderByMoltDateAsc(Invertebrate invertebrate);
}
