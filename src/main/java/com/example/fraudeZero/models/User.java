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
    @Column(nullable = false, unique = true)
    @OneToOne(cascade = CascadeType.ALL)
    private BankAccount accountNumber;

    public UUID getIdUser() {
        return idUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public String getCpfUser() {
        return cpfUser;
    }

    public BankAccount getAccountNumber() {
        return accountNumber;
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

    public void setAccountNumber(BankAccount accountNumber) {
        this.accountNumber = accountNumber;
    }
}
