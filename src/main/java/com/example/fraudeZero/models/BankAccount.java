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
    @OneToOne(cascade = CascadeType.ALL)
    private UUID idBankAccount;
    @Column(nullable = false, unique = true)
    private String owner;
    @Column(nullable = false)
    private BigDecimal transferLimit;
    @Column(nullable = false)
    private BigDecimal bankBalance;

    public UUID getIdBankAccount() {
        return idBankAccount;
    }

    public String getOwner() {
        return owner;
    }

    public BigDecimal getTransferLimit() {
        return transferLimit;
    }

    public BigDecimal getBankBalance() {
        return bankBalance;
    }

    public void setIdBankAccount(UUID idBankAccount) {
        this.idBankAccount = idBankAccount;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setTransferLimit(BigDecimal transferLimit) {
        this.transferLimit = transferLimit;
    }

    public void setBankBalance(BigDecimal bankBalance) {
        this.bankBalance = bankBalance;
    }
}
