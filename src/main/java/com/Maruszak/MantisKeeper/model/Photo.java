package com.Maruszak.MantisKeeper.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Photo {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    @NotNull
    private String link;

    @Column
    @NotNull
    private String publicID;

    @Column
    private LocalDateTime added;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "invert_id" , nullable = false)
    private Invertebrate invertebrate;

    public Photo() {
    }

    public Photo(String link, String publicID, LocalDateTime added, Invertebrate invertebrate) {
        this.link = link;
        this.publicID = publicID;
        this.added = added;
        this.invertebrate = invertebrate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPublicID() {
        return publicID;
    }

    public void setPublicID(String publicID) {
        this.publicID = publicID;
    }

    public LocalDateTime getAdded() {
        return added;
    }

    public void setAdded(LocalDateTime added) {
        this.added = added;
    }

    public Invertebrate getInvertebrate() {
        return invertebrate;
    }

    public void setInvertebrate(Invertebrate invertebrate) {
        this.invertebrate = invertebrate;
    }
}
