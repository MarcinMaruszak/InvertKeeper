package com.Maruszak.MantisKeeper.model;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
public class Instar {
    private L l;
    private LocalDate date;

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
}
