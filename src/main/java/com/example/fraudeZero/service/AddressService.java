package com.example.fraudeZero.service;

import com.example.fraudeZero.dtos.AddressRecord;
import com.example.fraudeZero.models.Address;
import com.example.fraudeZero.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;
}