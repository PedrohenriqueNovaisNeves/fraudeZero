package com.example.fraudeZero.service;

import com.example.fraudeZero.models.CepResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class BrasilApiService {

    @Autowired
    RestTemplate restTemplate;

    public CepResponse getAddressByCep(String cep){
        String url = "https://brasilapi.com.br/api/cep/v1/{cep}";
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(url)
                .buildAndExpand(cep.replaceAll("[^0-9]", ""));

        try{
            return restTemplate.getForObject(builder.toUriString(), CepResponse.class);
        }catch (Exception e){
            e.getMessage();
            return null;
        }
    }
}
