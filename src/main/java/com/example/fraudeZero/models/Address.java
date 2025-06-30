package com.example.fraudeZero.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_ADDRESSES")
@Getter
@Setter
public class Address {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idAddress;
    @Column(nullable = false)
    private String publicPlace;
    @Column(nullable = false, unique = true)
    private String zipCode;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accountId", referencedColumnName = "idAccount")
    private BankAccount bankAccount;
    @Column(unique = true, nullable = false)
    private String title;

    public UUID getIdAddress() {
        return idAddress;
    }

    public String getPublicPlace() {
        return publicPlace;
    }

    public String getZipCode() {
        return zipCode;
    }

    public BankAccount getBankAccount(){
        return bankAccount;
    }

    public String getTitle(){
        return title;
    }

    public void setIdAddress(UUID idAddress) {
        this.idAddress = idAddress;
    }

    public void setPublicPlace(String publicPlace) {
        this.publicPlace = publicPlace;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setBankAccount(BankAccount bankAccount){
        this.bankAccount = bankAccount;
    }

    public void setTitle(String title){
        this.title = title;
    }
}
