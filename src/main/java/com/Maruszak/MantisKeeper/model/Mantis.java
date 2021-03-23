package com.Maruszak.MantisKeeper.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Mantis extends Invertebrate{

    @Column
    @NotNull
    private MantisInstar instar;

    public MantisInstar getInstar() {
        return instar;
    }

    public void setInstar(MantisInstar instar) {
        this.instar = instar;
    }
}
