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
    private String origimAccount;
    @Column(nullable = false)
    private String targetAccount;
    @Column(nullable = false)
    private BigDecimal value;
    private String descriptionTransfer;
    private LocalDateTime dateTime;

    public UUID getTransferId() {
        return transferId;
    }

    public String getOrigimAccount() {
        return origimAccount;
    }

    public String getTargetAccount() {
        return targetAccount;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getDescriptionTransfer() {
        return descriptionTransfer;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setTransferId(UUID transferId) {
        this.transferId = transferId;
    }

    public void setOrigimAccount(String origimAccount) {
        this.origimAccount = origimAccount;
    }

    public void setTargetAccount(String targetAccount) {
        this.targetAccount = targetAccount;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public void setDescriptionTransfer(String descriptionTransfer) {
        this.descriptionTransfer = descriptionTransfer;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
