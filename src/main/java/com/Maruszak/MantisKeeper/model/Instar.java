package com.Maruszak.MantisKeeper.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Instar {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int id;

    @Column
    @NotNull
    private L l;

    @Column
    private LocalDate date;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "invertebrate_id")
    private Invertebrate invertebrate;

    public Instar() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public L getL() {
        return l;
    }

    public void setL(L l) {
        this.l = l;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Invertebrate getInvertebrate() {
        return invertebrate;
    }

    public void setInvertebrate(Invertebrate invertebrate) {
        this.invertebrate = invertebrate;
    }

    @Override
    public String toString() {
        return "Instar{" +
                "id=" + id +
                ", l=" + l +
                ", date=" + date +
                '}';
    }
}
