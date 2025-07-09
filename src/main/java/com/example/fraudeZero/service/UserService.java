package com.example.fraudeZero.service;

import com.example.fraudeZero.dtos.UserRecord;
import com.example.fraudeZero.models.User;
import com.example.fraudeZero.repository.BankAccountRepository;
import com.example.fraudeZero.repository.UserRepository;
import com.example.fraudeZero.service.validations.ValidationsUser;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ValidationsUser validationsUser;

    @Transactional
    public void saveUser(UserRecord userRecord){
        var userModel = new User();
        BeanUtils.copyProperties(userRecord, userModel);

        if(validationsUser.validatePassword(userModel.getPassword()) || validationsUser.validateEmail(userModel.getEmail()) || validationsUser.validateCpf(userModel.getCpfUser())){
            return;
        }

        userRepository.save(userModel);
    }

    public List<User> listAllUsers(){
        return userRepository.findAll();
    }

    public User listOneUser(UUID id){
        return userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found"));
    }

    public Object updateUserData(UUID id, User userModel){
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()){
            throw new RuntimeException("User not found");
        }

        var newUser = user.get();

        newUser.setNameUser(userModel.getNameUser());
        newUser.setCpfUser(userModel.getCpfUser());

        return userRepository.save(newUser);
    }

    public void deleteAllUsers(){
        userRepository.deleteAll();
    }

    public void deleteOneUser(UUID id){
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()){
            throw new RuntimeException("User not found");
        }

        userRepository.delete(user.get());
    }

    public Object loginUser(String cpfUser, String password){

        JSONObject json = new JSONObject(cpfUser);
        String cpf = json.getString("cpfUser");

        return userRepository.findByCpfUser(cpf);
    }
}