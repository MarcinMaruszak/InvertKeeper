package com.Maruszak.MantisKeeper.repository;

import com.Maruszak.MantisKeeper.model.Invertebrate;
import com.Maruszak.MantisKeeper.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvertebrateRepository extends JpaRepository<Invertebrate , Long> {

    List<Invertebrate> findAllByUser(User user);

}
