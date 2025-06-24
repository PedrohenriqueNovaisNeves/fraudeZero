package com.example.fraudeZero.dtos;

import jakarta.validation.constraints.NotBlank;

public record AddressRecord(@NotBlank String title, @NotBlank String publicPlace, @NotBlank String zipCode, @NotBlank String nameUser) {
}
