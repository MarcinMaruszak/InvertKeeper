package com.Maruszak.MantisKeeper.DTO;

import com.Maruszak.MantisKeeper.model.Instar;
import com.Maruszak.MantisKeeper.model.Invertebrate;

import javax.validation.constraints.NotNull;
import java.util.List;

public class InvertDTO {

    @NotNull
    private Invertebrate invertebrate;

    @NotNull
    private List<Instar> instars;


    public InvertDTO() {
    }

    public Invertebrate getInvertebrate() {
        return invertebrate;
    }

    public void setInvertebrate(Invertebrate invertebrate) {
        this.invertebrate = invertebrate;
    }

    public List<Instar> getInstars() {
        return instars;
    }

    public void setInstars(List<Instar> instars) {
        this.instars = instars;
    }

}
