package com.example.fraudeZero.controllers;

import com.example.fraudeZero.dtos.TransferRecord;
import com.example.fraudeZero.models.Adress;
import com.example.fraudeZero.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    @Autowired
    TransferService transferService;

    @PostMapping("/sendTransfer")
    public ResponseEntity<Object> sendTransfer(@Valid @RequestBody TransferRecord transferRecord) {
        try {
            Object result = transferService.sendTransfer(
                    transferRecord.origimAccount(),
                    transferRecord.targetAccount(),
                    transferRecord.value(),
                    transferRecord.adress(),
                    transferRecord.descriptionTransfer()
            );
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing transfer: " + e.getMessage());
        }
    }

    @GetMapping("/validateEmail")
    public ResponseEntity<String> validateEmail(
            @RequestParam String token,
            @RequestParam String origimAccount,
            @RequestParam String destinationAccount,
            @RequestParam BigDecimal value,
            @RequestParam String location,
            @RequestParam String description) {
        try {
            Adress adress = Adress.valueOf(location.toUpperCase());
            transferService.validateEmail(token, origimAccount, destinationAccount, value, adress, description);
            return ResponseEntity.ok("Validation processed. Check the transaction status.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid location. Allowed values are: CASA, TRABALHO, FACULDADE");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error validating email: " + e.getMessage());
        }
    }
}
