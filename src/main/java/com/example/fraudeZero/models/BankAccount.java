package com.example.fraudeZero.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "TB_BANKACCOUNT")
public class BankAccount {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idAccount;
    @OneToOne
    @JoinColumn(name = "owner")
    private User user;
    private BigDecimal bankBalance;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cpfUser", unique = true)
    private User pixKey;

    public UUID getIdAccount() {
        return idAccount;
    }

    public User getUser() {
        return user;
    }

    public BigDecimal getBankBalance() {
        return bankBalance;
    }

    public User getPixKey() {
        return pixKey;
    }

    public void setIdAccount(UUID idAccount) {
        this.idAccount = idAccount;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setBankBalance(BigDecimal bankBalance) {
        this.bankBalance = bankBalance;
    }

    public void setPixKey(User pixKey) {
        this.pixKey = pixKey;
    }
}
