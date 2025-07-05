package com.example.fraudeZero.repository;

import com.example.fraudeZero.models.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransferRepository extends JpaRepository<Transfer, UUID> {

    List<Transfer> findByOrigimAccount(String origimAccount);

}
