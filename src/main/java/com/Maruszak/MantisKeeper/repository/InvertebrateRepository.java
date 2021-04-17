package com.Maruszak.MantisKeeper.repository;

import com.Maruszak.MantisKeeper.model.Invertebrate;
import com.Maruszak.MantisKeeper.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InvertebrateRepository extends JpaRepository<Invertebrate , Long> {

    List<Invertebrate> findAllByUser(User user);

    List<Invertebrate> findAllByUserAndAliveTrue(User user);

    List<Invertebrate> findAllByUserAndAliveFalse(User user);

    Optional<Invertebrate> findById(UUID id);

    List<Invertebrate> findTop10ByOrderByAddedDesc();

    void deleteById(UUID id);

    Page<Invertebrate> findAllByAliveTrue(Pageable pageable);
}
