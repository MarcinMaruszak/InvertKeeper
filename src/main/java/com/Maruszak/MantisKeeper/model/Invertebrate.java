package com.Maruszak.MantisKeeper.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public abstract class Invertebrate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int ID;

    @Column
    private String name;

    @Column
    @NotEmpty
    private String specie;

    @Column
    private LocalDate birth;

    @Column
    private LocalDate acquired;

    @Column
    @NotNull
    private Sex sex;

    @Column
    @NotNull
    private Instar instar;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    public Invertebrate() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecie() {
        return specie;
    }

    public void setSpecie(String specie) {
        this.specie = specie;
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

    public Instar getInstar() {
        return instar;
    }

    public void setInstar(Instar instar) {
        this.instar = instar;
    }
}
