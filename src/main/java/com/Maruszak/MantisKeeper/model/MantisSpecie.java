package com.Maruszak.MantisKeeper.model;

public enum MantisSpecie implements Specie{
    O("Other"),
    AF("African Mantis - Sphodromantis lineola"),
    DLM("Dead Leaf Mantis – Deroplatys desiccata"),
    GSM("Gambian Spotted-Eye Mantis – Pseudoharpax virescens"),
    GHM("Ghost mantis – Phyllocrania paradoxa"),
    GAM("Giant Asian Mantis – Hierodula membranacea"),
    IFM("Indian Flower Mantis – Creobroter pictipennis"),
    OM("Orchid Mantis – Hymenopus coronatus"),
    SFM("Spiny Flower Mantis – Pseudocreobotra wahlbergii"),
    UM("Unicorn Mantis – Pseudovates arizonae"),
    EPM("Egyptian Pygmy Mantis – Miomantis paykullii"),
    EM("European Mantis – Mantis religiosa"),
    BM("Budwing Mantis – Parasphendale affinis"),
    TM("Thistle Mantis – Blepharopsis mendica"),
    DFM("Devils Flower Mantis – Idolomantis diabolica"),
    WVM("Wandering Violin Mantis – Gongylus gongylodes"),
    CHM("Chinese Mantis – Tenodera sinensis"),
    CM("Carolina mantis – Stagmomantis carolina"),
    SM("Stick mantis - Heterochaeta strachani"),
    BXM("Boxer mantis - Odontomantis sp."),
    GM("Carolina mantis - Stagmomantis carolina"),
    DPM("Desert Pebble Mantis - Eremiaphila zetterstedti"),
    AT("AFRICAN TWIG MANTIS - Popa spurca"),
    MM("Plistospilota guineensis - Mega Mantis"),
    AH("Alien Head Mantis - Idolomorpha lateralis");


    private final String name;

    public String getName() {
        return name;
    }

    private MantisSpecie(String name){
        this.name = name;
    }
}

