package com.Maruszak.MantisKeeper.repository;

import com.Maruszak.MantisKeeper.model.Message;
import com.Maruszak.MantisKeeper.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByReceiverOrderBySentDateTimeDesc(User user);

    Optional<Message> findById(UUID id);

    List<Message> findAllByReceiverAndSeenFalse(User user);

    List<Message> findAllBySenderOrderBySentDateTimeDesc(User user);
}
