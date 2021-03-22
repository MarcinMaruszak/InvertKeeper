package com.Maruszak.MantisKeeper.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private String email;
}
