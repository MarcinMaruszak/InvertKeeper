package com.Maruszak.MantisKeeper.DTO;

public class ChangePasswordDTO {

    private String oldPass;

    private String newPass;

    private String token;

    public ChangePasswordDTO() {
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
