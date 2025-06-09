package com.example.fraudeZero.controllers;


import com.example.fraudeZero.dtos.BankAccountRecord;
import com.example.fraudeZero.models.BankAccount;
import com.example.fraudeZero.service.BankAccountService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/bankAccount")
@RestController
public class BankAccountController {

    @Autowired
    BankAccountService bankAccountService;

    @PostMapping("/saveAccount")
    public ResponseEntity<Object> saveBankAccount(@Valid @RequestBody BankAccountRecord bankAccountRecord){
        var account = new BankAccount();
        BeanUtils.copyProperties(bankAccountRecord, account);

        bankAccountService.saveAccount(account);

        return ResponseEntity.status(HttpStatus.CREATED).body("Account registered");
    }
}
