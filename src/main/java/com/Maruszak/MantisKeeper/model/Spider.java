package com.Maruszak.MantisKeeper.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Spider extends Invertebrate{

    @Column
    private SpiderSpecie specie;

    public Spider() {
    }

    public SpiderSpecie getSpecie() {
        return specie;
    }

    public void setSpecie(SpiderSpecie specie) {
        this.specie = specie;
    }
}
