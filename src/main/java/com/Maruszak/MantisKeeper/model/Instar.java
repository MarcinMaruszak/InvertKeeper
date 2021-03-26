package com.Maruszak.MantisKeeper.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Instar {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)

    @Column
    private L l;

    @Column
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "invertebrate_id", nullable = false)
    private Invertebrate invertebrate;

    public Instar() {
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
}
