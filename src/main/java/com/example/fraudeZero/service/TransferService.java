package com.example.fraudeZero.service;

import com.example.fraudeZero.models.Adress;
import com.example.fraudeZero.models.BankAccount;
import com.example.fraudeZero.models.Status;
import com.example.fraudeZero.models.Transfer;
import com.example.fraudeZero.repository.BankAccountRepository;
import com.example.fraudeZero.repository.TransferRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransferService {

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    TransferRepository transferRepository;

    public boolean validateBalance(BigDecimal balance, BigDecimal value) {

        if (balance.compareTo(value) < 0) {
            return true;
        }

        return false;
    }

    public boolean validationTimeIntermediary() {

        LocalTime now = LocalTime.now();
        LocalTime intermediary = LocalTime.of(20, 0);

        if (now.isAfter(intermediary)) {
            return true;
        }
        return false;

    }

    public boolean validationTimeLimit() {

        LocalTime now = LocalTime.now();
        LocalTime limit = LocalTime.of(23, 0);
        LocalTime interval = LocalTime.of(8, 00);

        if ((now.isAfter(limit)) || (now.isBefore(interval))) {
            return true;
        }
        return false;
    }


    public boolean validateLocation(String location) {
        if (location == null || location.isBlank()) {
            return true;
        }

        return !(location.equalsIgnoreCase("CASA")
                || location.equalsIgnoreCase("FACULDADE")
                || location.equalsIgnoreCase("TRABALHO"));

    }

    public Object sendTransfer(String origimAccount, String destinationAccount, BigDecimal value, String location, String description) {
        Optional<BankAccount> origin = bankAccountRepository.findByPixKey(origimAccount);

        if (origin.isEmpty()) {
            throw new RuntimeException("Account not found");
        }

        Optional<BankAccount> destination = bankAccountRepository.findByPixKey(destinationAccount);

        if (destination.isEmpty()) {
            throw new RuntimeException("Account not found");
        }

        if (validateBalance(origin.get().getBankBalance(), value)) {
            throw new RuntimeException("insufficient balance");
        } else if (validationTimeIntermediary()) {
            BigDecimal limitValue = BigDecimal.valueOf(800.0);

            if (value.compareTo(limitValue) > 0) {
                throw new RuntimeException("It is not possible to make transfers larger than 800 reais after 20:00");
            }
            BankAccount newAccount = origin.get();
            BigDecimal extract = newAccount.getBankBalance().subtract(value);
            newAccount.setBankBalance(extract);
            bankAccountRepository.save(newAccount);
            BankAccount newDestination = destination.get();
            BigDecimal deposit = newDestination.getBankBalance().add(value);
            newDestination.setBankBalance(deposit);
            return bankAccountRepository.save(newDestination);
        } else if (validationTimeLimit()) {
            throw new RuntimeException("It is not possible to make transfers after 23:00");
        } else if (!validateLocation(location)) {
            throw new RuntimeException("Invalid location for transfer");
        }

        BankAccount newAccount = origin.get();
        BigDecimal extract = newAccount.getBankBalance().subtract(value);
        newAccount.setBankBalance(extract);
        bankAccountRepository.save(newAccount);
        BankAccount newDestination = destination.get();
        BigDecimal deposit = newDestination.getBankBalance().add(value);
        newDestination.setBankBalance(deposit);
        bankAccountRepository.save(newDestination);

        var transfer = new Transfer();
        transfer.setOrigimAccount(newAccount.getPixKey());
        transfer.setTargetAccount(newDestination.getPixKey());
        transfer.setValue(value);
        transfer.setDescriptionTransfer(description);
        transfer.setDateTime(LocalDateTime.now());

        return transferRepository.save(transfer);
    }
}