package com.Maruszak.MantisKeeper.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Mantis extends Invertebrate{

    @Column
    private MantisSpecie specie;

    public Mantis() {
    }

    public MantisSpecie getSpecie() {
        return specie;
    }

    public void setSpecie(MantisSpecie specie) {
        this.specie = specie;
    }

}
