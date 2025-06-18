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
    @Column(nullable = false)
    private String zipCode;
}
