package com.example.fraudeZero.service;

import com.example.fraudeZero.dtos.AddressRecord;
import com.example.fraudeZero.models.Address;
import com.example.fraudeZero.models.BankAccount;
import com.example.fraudeZero.models.CepResponse;
import com.example.fraudeZero.models.User;
import com.example.fraudeZero.repository.AddressRepository;
import com.example.fraudeZero.repository.BankAccountRepository;
import com.example.fraudeZero.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BrasilApiService brasilApiService;

    @Transactional
    public Object saveAddress(String pixKey, AddressRecord addressRecord){
        JSONObject json = new JSONObject(pixKey);

        String searchAccount = json.getString("pixKey");

        Optional<BankAccount> account = bankAccountRepository.findByPixKey(searchAccount);

        if(account.isEmpty()){
            throw new RuntimeException("account not found");
        }

        var address = new Address();
        BeanUtils.copyProperties(addressRecord, address);

        address.setBankAccount(account.get());

        String cep = addressRecord.zipCode().replaceAll("[^0-9]", "").trim();
        CepResponse response = brasilApiService.getAddressByCep(cep);

        if(response == null || response.getCep() == null){
            throw new RuntimeException("The zip code provided does not exist.");
        }

        String apiStreet = response.getStreet() != null ? response.getStreet().toLowerCase() : "";
        String inputPublicPlace = addressRecord.publicPlace().toLowerCase();

        if(!apiStreet.contains(inputPublicPlace) && !inputPublicPlace.contains(apiStreet)){
            throw new RuntimeException("The street does not match the postal code address.");
        }


        return addressRepository.save(address);
    }

    public Object listOneAddress(String zipCode){
        Optional<Address> address = addressRepository.findByZipCode(zipCode);

        if(address.isEmpty()){
            throw new RuntimeException("Address not found");
        }

        var address1 = new Address();

        address1.setTitle(address.get().getTitle());
        address1.setPublicPlace(address.get().getPublicPlace());
        address1.setZipCode(address.get().getZipCode());

        return address1;
    }

    public List<Address> listAllAddresses(){
        return addressRepository.findAll();
    }

    public List<Address> listAddressesByAccount(String pixKey){
        JSONObject json = new JSONObject(pixKey);
        String pixKey1 = json.getString("pixKey");

        Optional<BankAccount> account = bankAccountRepository.findByPixKey(pixKey1);

        if(account.isEmpty()){
            throw new RuntimeException("Account not found");
        }

        var newAccount = account.get();

        return addressRepository.findByBankAccount_idAccount(newAccount.getIdAccount());
    }

    public Object updateAddress(String zipCode, AddressRecord addressRecord){
        Optional<Address> address = addressRepository.findByZipCode(zipCode);

        if(address.isEmpty()){
            throw new RuntimeException("Address not found");
        }

        var newAddress = address.get();
        var addressModel = new Address();
        BeanUtils.copyProperties(addressRecord, addressModel);

        newAddress.setTitle(addressModel.getTitle());
        newAddress.setZipCode(addressModel.getZipCode());
        newAddress.setPublicPlace(addressModel.getPublicPlace());

        return addressRepository.save(newAddress);
    }

    public void deleteAllAddresses(){
        addressRepository.deleteAll();
    }

    public void deleteOneAddress(String zipCode){
        Optional<Address> address = addressRepository.findByZipCode(zipCode);

        if(address.isEmpty()){
            throw new RuntimeException("Address not found");
        }

        addressRepository.delete(address.get());
    }
}