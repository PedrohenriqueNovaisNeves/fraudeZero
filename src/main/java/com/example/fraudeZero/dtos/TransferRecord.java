package com.example.fraudeZero.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransferRecord(@NotBlank String origimAccount, @NotBlank String targetAccount, @NotNull BigDecimal value, @NotBlank String descriptionTransfer, @NotNull Adress adress) {
}
