package com.Maruszak.MantisKeeper.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "insectType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MantisSpecie.class, name = "MantisSpecie"),
        @JsonSubTypes.Type(value = SpiderSpecie.class, name = "SpiderSpecie")
})
public interface Specie {

    String getEnglishName();

    String getLatinName();

    String getFullName();
}
