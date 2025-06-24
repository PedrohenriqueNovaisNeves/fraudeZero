package com.example.fraudeZero.service;

import com.example.fraudeZero.dtos.AddressRecord;
import com.example.fraudeZero.models.Address;
import com.example.fraudeZero.models.BankAccount;
import com.example.fraudeZero.repository.AddressRepository;
import com.example.fraudeZero.repository.BankAccountRepository;
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

    @Transactional
    public Object saveAddress(AddressRecord addressRecord){
        var address = new Address();
        BeanUtils.copyProperties(addressRecord, address);

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