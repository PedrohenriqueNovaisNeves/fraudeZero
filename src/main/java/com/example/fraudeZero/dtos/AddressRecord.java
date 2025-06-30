package com.example.fraudeZero.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddressRecord(@NotBlank(message = "The zip code cannot be blank.") @Pattern String title, @NotBlank String publicPlace, @NotBlank String zipCode) {
}
