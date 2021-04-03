package com.Maruszak.MantisKeeper.model;

public enum Sex {
    UNK("Unknown"),
    M("Male"),
    F("Female");

    private final String name;

    public String getName() {
        return name;
    }

    private Sex(String name){
        this.name = name;
    }
}
