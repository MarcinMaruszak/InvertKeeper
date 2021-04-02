package com.Maruszak.MantisKeeper.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Instar {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column
    @NotNull
    private L l;

    @Column
    private LocalDate moltDate;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "invertebrate_id")
    private Invertebrate invertebrate;

    public Instar() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public L getL() {
        return l;
    }

    public void setL(L l) {
        this.l = l;
    }

    public LocalDate getMoltDate() {
        return moltDate;
    }

    public void setMoltDate(LocalDate moltDate) {
        this.moltDate = moltDate;
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
                ", date=" + moltDate +
                '}';
    }
}
