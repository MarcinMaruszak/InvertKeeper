package com.Maruszak.MantisKeeper.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Message {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private String subject;

    @Column(length = 500, columnDefinition="Text")
    private String message;

    @JsonBackReference(value = "sender")
    @ManyToOne
    @JoinColumn(name="sender_id", nullable=false)
    private User sender;

    @JsonBackReference(value = "receiver")
    @ManyToOne
    @JoinColumn(name="receiver_id", nullable=false)
    private User receiver;

    @Column
    private LocalDateTime sentDateTime;

    @Column
    @NotNull
    private boolean seen;

    public Message() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public LocalDateTime getSentDateTime() {
        return sentDateTime;
    }

    public void setSentDateTime(LocalDateTime sentDateTime) {
        this.sentDateTime = sentDateTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}
