package com.example.fraudeZero.repository;

import com.example.fraudeZero.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {

    Optional<Address> findByZipCode(String zipCode);

    List<Address> findByBankAccount_idAccount(UUID accountId);

    Optional<Address> findByTitle(String title);
}
