package com.example.fraudeZero.service;

import com.example.fraudeZero.dtos.AddressRecord;
import com.example.fraudeZero.dtos.BankAccountRecord;
import com.example.fraudeZero.models.BankAccount;
import com.example.fraudeZero.models.User;
import com.example.fraudeZero.repository.AddressRepository;
import com.example.fraudeZero.repository.BankAccountRepository;
import com.example.fraudeZero.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BankAccountService {

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public Object saveAccount(BankAccountRecord bankAccountRecord){
        var account = new BankAccount();
        Optional<User> user = userRepository.findById(bankAccountRecord.id());

        if(user.isEmpty()){
            throw new RuntimeException("user not found");
        }

        try {

            User newUser = user.get();
            BeanUtils.copyProperties(bankAccountRecord, account);
            account.setUser(newUser);
            return bankAccountRepository.save(account);

        } catch (DataAccessException e) {

            throw new RuntimeException("Error saving account to the database: " + e.getMessage(), e);

        } catch (BeansException e) {

            throw new IllegalArgumentException("Error mapping account properties: " + e.getMessage(), e);

        } catch (RuntimeException e) {

            throw e;

        } catch (Exception e) {

            throw new RuntimeException("Internal error processing account: " + e.getMessage(), e);

        }


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