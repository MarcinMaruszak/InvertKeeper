package com.Maruszak.MantisKeeper.model;

public enum SpiderSpecie implements Specie {
    O("Other", "Other"),
    BDP("Brazilian Dwarf Pinkleg", "Kochiana brunnipes"),
    flb("Feather Leg Baboon", "Stromatopelma calceatum"),
    VS("Venezuelan Suntiger", "Psalmopoeus irminia"),
    TC("Trinidad Chevron", "Psalmopoeus cambridgei"),
    PB("Panama Blonde" ,"Psalmopoeus pulcher"),
    GBBT("Greenbottle blue tarantula", "Chromatopelma cyaneopubescens"),
    BB(" Brazilian black", "Grammostola pulchra"),
    CR("Chile Rose","Grammostola porteri"),
    CGK("Chaco Golden Knee","Grammostola pulcheripes"),
    MRK("Mexican Red Knee","Brachypelma smithi"),
    CH("Curly Hair", "Brachypelma albopilosum"),
    CGB("Chile Gold Burst", "Paraphysa parvula"),
    RJS("Regal Jumping Spider", "Phidippus regius"),
    DBT("Arizona Blonde Tarantulas", "Aphonopelma chalcodes"),
    GBE("Goliath birdeater", "Theraphosa blondi"),
    GSO("Gooty sapphire ornamental", "Poecilotheria metallica"),
    CBT("Cobalt blue tarantula", "Cyriopagopus lividus");

    private final String englishName;
    private final String latinName;


    SpiderSpecie(String englishName, String latinName) {
        this.englishName = englishName;
        this.latinName = latinName;
    }

    @Override
    public String getEnglishName() {
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
