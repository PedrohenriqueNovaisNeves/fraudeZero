package com.example.fraudeZero.dtos;

import com.example.fraudeZero.models.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record BankAccountRecord(@NotNull BigDecimal bankBalance, @NotBlank String pixKey, @NotBlank String password){
}
