package com.example.fraudeZero.controllers;

import com.example.fraudeZero.dtos.UserRecord;
import com.example.fraudeZero.models.User;
import com.example.fraudeZero.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/saveUser")
    public ResponseEntity<Object> saveUser(@Valid @RequestBody UserRecord userRecord){
        var user = new User();
        BeanUtils.copyProperties(userRecord, user);

        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered with id, " + user.getIdUser());
    }

    @PostMapping("/loginUser")
    public ResponseEntity<Object> loginUser(@RequestBody String cpfUser, String password){
        Object result = userService.loginUser(cpfUser, password);
        return ResponseEntity.status(HttpStatus.OK).body("Login completed with successfully!!");
    }
}
