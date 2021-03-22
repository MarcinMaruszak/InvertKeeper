package com.Maruszak.MantisKeeper.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@MappedSuperclass
public abstract class Invertebrate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int ID;

    @Column
    @NotEmpty
    private String specie;

    @Column
    private LocalDate birth;

}
