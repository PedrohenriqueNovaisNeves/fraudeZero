package com.example.fraudeZero.repository;

import com.example.fraudeZero.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {


}
