package com.Maruszak.MantisKeeper.DTO;

public class PasswordTokenDTO {

    private String password;

    private String token;

    public PasswordTokenDTO() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
