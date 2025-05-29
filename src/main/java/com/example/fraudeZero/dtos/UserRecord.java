package com.example.fraudeZero.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserRecord(@NotBlank String nameUser, @NotBlank String cpfUser) {
}
