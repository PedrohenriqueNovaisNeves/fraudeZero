package com.example.fraudeZero.dtos;

import com.example.fraudeZero.service.validations.ExistingCepAndAddress;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddressRecord(@NotBlank(message = "Title cannot be blank.")
                            String title,

                            @NotBlank(message = "The street cannot be blank.")
                            String publicPlace,

                            @NotBlank(message = "The zip code cannot be blank.")
                            @Pattern(regexp = "^\\d{5}-\\d{3}$|^\\d{8}$", message = "The zip code must be in the format 12345-678 or 12345678.")
                            @ExistingCepAndAddress(message = "The zip code does not exist or the street does not match the address.")
                            String zipCode,

                            @NotBlank(message = "the pix key cannot be blank")
                            String pixKey
) {
}
