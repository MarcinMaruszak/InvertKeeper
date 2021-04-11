package com.Maruszak.MantisKeeper.DTO;

import javax.validation.constraints.NotNull;

public class PasswordsDTO {

    @NotNull
    private String oldPass;

    @NotNull
    private String newPass;

    public PasswordsDTO() {
    }

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }
}
