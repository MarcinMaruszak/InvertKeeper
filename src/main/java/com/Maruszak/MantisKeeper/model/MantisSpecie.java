package com.Maruszak.MantisKeeper.model;

public enum MantisSpecie implements Specie{
    O("Other", "Other"),
    AF("African Mantis","Sphodromantis lineola"),
    DLM("Dead Leaf Mantis","Deroplatys desiccata"),
    GSM("Gambian Spotted" , "Eye MantisPseudoharpax virescens"),
    GHM("Ghost mantis" , "Phyllocrania paradoxa"),
    GAM("Giant Asian Mantis" , "Hierodula membranacea"),
    IFM("Indian Flower Mantis" , "Creobroter pictipennis"),
    OM("Orchid Mantis" , "Hymenopus coronatus"),
    SFM("Spiny Flower Mantis" , "Pseudocreobotra wahlbergii"),
    UM("Unicorn Mantis" , "Pseudovates arizonae"),
    EPM("Egyptian Pygmy Mantis"  , "Miomantis paykullii"),
    EM("European Mantis" , "Mantis religiosa"),
    BM("Budwing Mantis" , "Parasphendale affinis"),
    TM("Thistle Mantis" , "Blepharopsis mendica"),
    DFM("Devils Flower Mantis" , "Idolomantis diabolica"),
    WVM("Wandering Violin Mantis" ,"Gongylus gongylodes"),
    CHM("Chinese Mantis" ,"Tenodera sinensis"),
    CM("Carolina mantis" ,"Stagmomantis carolina"),
    SM("Stick mantis" ,"Heterochaeta strachani"),
    BXM("Boxer mantis" ,"Odontomantis sp."),
    GM("Carolina mantis" ,"Stagmomantis carolina"),
    DPM("Desert Pebble Mantis" ,"Eremiaphila zetterstedti"),
    AT("African Twig Mantis" ,"Popa spurca"),
    MM("Plistospilota guineensis" ,"Mega Mantis"),
    AH("Alien Head Mantis" ,"Idolomorpha lateralis");


    private final String englishName;
    private final String latinName;

    MantisSpecie(String englishName, String latinName) {
        this.englishName = englishName;
        this.latinName = latinName;
    }

    @Override
    public String getEnglishName(){
        return englishName;
    }

    @Override
    public String getLatinName() {
        return latinName;
    }

    @Override
    public String getFullName() {
        return englishName + " - " + latinName;
    }


}

