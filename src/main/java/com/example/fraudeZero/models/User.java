package com.example.fraudeZero.models;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "TB_USER")
public class User {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idUser;

    private String nameUser;

    @Column(nullable = false)
    private String cpfUser;
    private String email;
    private String password;
    @Column(nullable = false)
    private boolean emailValidated;
    @Column(length = 36)
    private String validationToken;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UUID getIdUser() {
        return idUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public String getCpfUser() {
        return cpfUser;
    }

    public boolean isEmailValidated(){
        return emailValidated;
    }

    public String getValidationToken(){
        return validationToken;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIdUser(UUID idUser) {
        this.idUser = idUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public void setCpfUser(String cpfUser) {
        this.cpfUser = cpfUser;
    }

    public void setEmailValidated(boolean emailValidated){
        this.emailValidated = emailValidated;
    }

    public void setValidationToken(String validationToken){
        this.validationToken = validationToken;
    }
}
