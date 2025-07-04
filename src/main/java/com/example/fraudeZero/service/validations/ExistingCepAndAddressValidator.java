package com.example.fraudeZero.service.validations;


import com.example.fraudeZero.dtos.AddressRecord;
import com.example.fraudeZero.models.CepResponse;
import com.example.fraudeZero.service.BrasilApiService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ExistingCepAndAddressValidator implements ConstraintValidator<ExistingCepAndAddress, AddressRecord> {

    @Autowired
    BrasilApiService brasilApiService;

    @Override
    public boolean isValid(AddressRecord addressRecord, ConstraintValidatorContext constraintValidatorContext){
        if(addressRecord == null){
            return true;
        }

        String cep = addressRecord.zipCode().replaceAll("[^0-9]", "").trim();
        if(cep.length() != 8){
            return false;
        }

        CepResponse response = brasilApiService.getAddressByCep(cep);
        if(response == null || response.getCep() == null){
            return false;
        }

        String apiStreet = response.getStreet() != null ? response.getStreet().toLowerCase() : "";
        String inputPublicPlace = addressRecord.publicPlace().toLowerCase();
        return apiStreet.contains(inputPublicPlace) || inputPublicPlace.contains(apiStreet);
    }
}
