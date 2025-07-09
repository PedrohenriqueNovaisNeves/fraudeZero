package com.example.fraudeZero.service.validations;

import com.example.fraudeZero.models.BankAccount;
import com.example.fraudeZero.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ValidationsBankAccount {

    @Autowired
    BankAccountRepository bankAccountRepository;

    public boolean validatePixKey(String pixKey){
        Optional<BankAccount> validation = bankAccountRepository.findByPixKey(pixKey);

        if(!validation.isEmpty()){
            throw new RuntimeException("Pix Key already registered");
        }

        return false;
    }
}
