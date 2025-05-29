package com.example.fraudeZero.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record BankAccountRecord(@NotNull String nameUser, @NotNull BigDecimal bankBalance, @NotBlank String pixKey){
}
