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
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "owner_id", referencedColumnName = "idUser")
    private User user;
    private BigDecimal bankBalance;
    @Column(nullable = false, unique = true)
    private String pixKey;

    public UUID getIdAccount() {
        return idAccount;
    }

    public User getUser() {
        return user;
    }

    public BigDecimal getBankBalance() {
        return bankBalance;
    }

    public String getPixKey() {
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

    public void setPixKey(String pixKey) {
        this.pixKey = pixKey;
    }
}
