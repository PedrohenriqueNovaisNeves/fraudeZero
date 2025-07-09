package com.example.fraudeZero.service.validations;

import com.example.fraudeZero.models.User;
import com.example.fraudeZero.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ValidationsUser {

    @Autowired
    UserRepository userRepository;

    public boolean validatePassword(String password){
        Optional<User> validation = userRepository.findByPassword(password);

        if(!validation.isEmpty()){
            throw new RuntimeException("Password already used");
        }

        return false;
    }

    public boolean validateEmail(String email){
        Optional<User> validation = userRepository.findByEmail(email);

        if(!validation.isEmpty()){
            throw new RuntimeException("Email already registered");
        }

        return false;
    }

    public boolean validateCpf(String cpf){
        Optional<User> validation = userRepository.findByCpfUser(cpf);

        if(!validation.isEmpty()){
            throw new RuntimeException("CPF already registered");
        }

        return false;
    }


}
