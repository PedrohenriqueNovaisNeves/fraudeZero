package com.example.fraudeZero.repository;

import com.example.fraudeZero.models.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {

    Optional<BankAccount> findByPixKey(String pixKey);
}
