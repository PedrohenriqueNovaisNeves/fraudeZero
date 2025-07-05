package com.example.fraudeZero.controllers;

import com.example.fraudeZero.dtos.AddressRecord;
import com.example.fraudeZero.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @PostMapping("/saveSecureAddress")
    public ResponseEntity<Object> saveAddress(@Valid @RequestBody AddressRecord addressRecord, String pixKey){

        addressService.saveAddress(pixKey, addressRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body("Secure address registered with successfully!");
    }
}
