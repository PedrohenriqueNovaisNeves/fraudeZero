package com.example.fraudeZero.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TB_TRANSFER")
public class Transfer {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID transferId;
    @Column(nullable = false)
    private BankAccount origimAccount;
    @Column(nullable = false)
    private BankAccount targetAccount;
    @Column(nullable = false)
    private BigDecimal value;
    private LocalDateTime dateTime;
    private Status status;

    public UUID getTransferId() {
        return transferId;
    }

    public BankAccount getOrigimAccount() {
        return origimAccount;
    }

    public BankAccount getTargetAccount() {
        return targetAccount;
    }

    public BigDecimal getValue() {
        return value;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setTransferId(UUID transferId) {
        this.transferId = transferId;
    }

    public void setOrigimAccount(BankAccount origimAccount) {
        this.origimAccount = origimAccount;
    }

    public void setTargetAccount(BankAccount targetAccount) {
        this.targetAccount = targetAccount;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
