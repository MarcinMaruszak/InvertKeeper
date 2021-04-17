package com.Maruszak.MantisKeeper.repository;

import com.Maruszak.MantisKeeper.model.Invertebrate;
import com.Maruszak.MantisKeeper.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    Optional<Photo> findById(UUID uuid);

    List<Photo> findAllByInvertebrateAndAvatarFalseOrderByAddedAsc(Invertebrate invertebrate);

    List<Photo> findAllByInvertebrateOrderByAddedAsc(Invertebrate invertebrate);

    Optional<Photo> findByInvertebrateAndAvatarTrue(Invertebrate invertebrate);

    void deleteAllByInvertebrate(Invertebrate invert);

    void deleteByIdIn(List<UUID> ids);

    List<Photo> findByIdIn(List<UUID> id);

    Optional<Photo> findTopByInvertebrateOrderByAddedDesc(Invertebrate invertebrate);

}


