package com.example.fraudeZero.dtos;

import com.example.fraudeZero.models.Adress;
import com.example.fraudeZero.models.BankAccount;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferRecord(@NotBlank String origimAccount, @NotBlank String targetAccount, @NotNull BigDecimal value, @NotBlank String descriptionTransfer, @NotNull Adress adress) {
}
