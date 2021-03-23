package com.Maruszak.MantisKeeper.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column
    @Email
    private String email;

    @Column
    @Size(min = 8)
    private String password;

    @Column
    private GrantedAuthority [] role;

    @Column
    @OneToMany(mappedBy = "user")
    @ElementCollection(targetClass= Invertebrate.class)
    private List<Invertebrate> invertebratesList;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public GrantedAuthority [] getRole() {
        return role;
    }

    public void setRole(GrantedAuthority [] role) {
        this.role = role;
    }

    public List<Invertebrate> getInvertebratesList() {
        return invertebratesList;
    }

    public void setInvertebratesList(List<Invertebrate> invertebratesList) {
        this.invertebratesList = invertebratesList;
    }
}
