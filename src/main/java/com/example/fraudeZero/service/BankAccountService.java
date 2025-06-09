package com.example.fraudeZero.service;

import com.example.fraudeZero.dtos.BankAccountRecord;
import com.example.fraudeZero.models.BankAccount;
import com.example.fraudeZero.repository.BankAccountRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BankAccountService {

    @Autowired
    BankAccountRepository bankAccountRepository;

    public BankAccount saveAccount(BankAccount account){
        return bankAccountRepository.save(account);
    }

    public List<BankAccount> listAllAccounts(){
        return bankAccountRepository.findAll();
    }

    public Object updateDataAccount(UUID id, BankAccount bankAccount){
        Optional<BankAccount> account = bankAccountRepository.findById(id);

        if(account.isEmpty()){
            throw new RuntimeException("Account not found");
        }

        var newAccount = account.get();

        newAccount.setBankBalance(bankAccount.getBankBalance());
        newAccount.setPixKey(bankAccount.getPixKey());

        return bankAccountRepository.save(newAccount);
    }

    public void deleteAllAccounts(){
        bankAccountRepository.deleteAll();
    }

    public void deleteOneAccounts(UUID id) {
        Optional<BankAccount> account = bankAccountRepository.findById(id);

        if (account.isEmpty()) {
            throw new RuntimeException("Account not found");
        }

        bankAccountRepository.delete(account.get());
    }


}