package com.Maruszak.MantisKeeper.DTO;

import javax.validation.constraints.NotNull;

public class ContactDTO {

    @NotNull
    private String type;

    @NotNull
    private String message;

    public ContactDTO() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
