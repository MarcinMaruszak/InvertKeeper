package com.Maruszak.MantisKeeper.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Mantis extends Invertebrate{

    public Mantis() {
    }
}
