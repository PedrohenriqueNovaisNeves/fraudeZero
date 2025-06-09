package com.example.fraudeZero.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record BankAccountRecord(@NotNull String nameUser, @NotNull BigDecimal bankBalance, @NotBlank String pixKey, @NotNull
                                UUID idUser){
}
