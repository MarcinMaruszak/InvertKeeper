package com.Maruszak.MantisKeeper.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Mantis.class, name = "Mantis"),
        @JsonSubTypes.Type(value = Spider.class, name = "Spider")
})
public abstract class Invertebrate {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    private String name;

    @Column
    private LocalDate birth;

    @Column
    private LocalDate acquired;

    @Column
    @NotNull
    private Sex sex;

    @Column
    @NotNull
    private Type insectType;

    @Column
    @NotNull
    private boolean alive;

    @Column
    private LocalDate death;

    @OneToMany(mappedBy = "invertebrate")
    @ElementCollection(targetClass = Instar.class)
    private List<Instar> instars;

    @Column
    private L lastInstar;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @OneToMany(mappedBy = "invertebrate")
    @ElementCollection(targetClass = Photo.class)
    private List<Photo> photos;

    @Transient
    private Photo avatar;

    @Column
    private LocalDateTime added;

    public Invertebrate() {
    }

    @PrePersist
    void lastInstar(){
        if(lastInstar==null){
            lastInstar=instars.get(instars.size()-1).getL();
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public LocalDate getAcquired() {
        return acquired;
    }

    public void setAcquired(LocalDate acquired) {
        this.acquired = acquired;
    }

    public List<Instar> getInstars() {
        return instars;
    }

    public void setInstars(List<Instar> instars) {
        this.instars = instars;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public LocalDate getDeath() {
        return death;
    }

    public void setDeath(LocalDate death) {
        this.death = death;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public Photo getAvatar() {
        return avatar;
    }

    public void setAvatar(Photo avatar) {
        this.avatar = avatar;
    }

    public LocalDateTime getAdded() {
        return added;
    }

    public void setAdded(LocalDateTime added) {
        this.added = added;
    }

    public Type getInsectType() {
        return insectType;
    }

    public void setInsectType(Type insectType) {
        this.insectType = insectType;
    }

    public L getLastInstar() {
        if(lastInstar==null){
            lastInstar=instars.get(instars.size()-1).getL();
        }
        return lastInstar;
    }

    public void setLastInstar(L lastInstar) {
        this.lastInstar = lastInstar;
    }

    @Override
    public String toString() {
        return "Invertebrate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birth=" + birth +
                ", acquired=" + acquired +
                ", sex=" + sex +
                ", insectType=" + insectType +
                ", alive=" + alive +
                ", death=" + death +
                ", lastInstar=" + lastInstar +
                ", avatar=" + avatar +
                ", added=" + added +
                '}';
    }
}

