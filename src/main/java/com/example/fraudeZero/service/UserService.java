package com.example.fraudeZero.service;

import com.example.fraudeZero.dtos.UserRecord;
import com.example.fraudeZero.models.User;
import com.example.fraudeZero.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User saveUser(UserRecord userRecord){

        var user = new User();
        BeanUtils.copyProperties(userRecord, user);

        return userRepository.save(user);
    }

    public List<User> listAllUsers(){
        return userRepository.findAll();
    }

    public User listOneUser(UUID id){
        return userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found"));
    }

    public Object showAccountData(UUID id){
        return userRepository.findByOwner(id);
    }
}