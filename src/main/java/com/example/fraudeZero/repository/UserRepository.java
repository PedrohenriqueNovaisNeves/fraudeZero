package com.example.fraudeZero.repository;

import com.example.fraudeZero.models.BankAccount;
import com.example.fraudeZero.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
