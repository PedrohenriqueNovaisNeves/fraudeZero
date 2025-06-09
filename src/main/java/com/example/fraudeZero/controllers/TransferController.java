package com.example.fraudeZero.controllers;

import com.example.fraudeZero.dtos.TransferRecord;
import com.example.fraudeZero.models.Adress;
import com.example.fraudeZero.models.Transfer;
import com.example.fraudeZero.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    @Autowired
    TransferService transferService;

    @PostMapping("sendTransfer")
    public ResponseEntity<Object> sendTransfer(@Valid @RequestBody TransferRecord transferRecord){
        var transfer = new Transfer();
        BeanUtils.copyProperties(transferRecord, transfer);

        transferService.sendTransfer(transfer.getOrigimAccount(), transfer.getTargetAccount(), transfer.getValue(), transfer.getAdress(), transfer.getDescriptionTransfer());

        return ResponseEntity.status(HttpStatus.OK).body("Transfer completed");
    }
}
