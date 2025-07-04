package com.example.fraudeZero.models;

public class CepResponse {

    private String cep;
    private String state;
    private String city;
    private String neighborhood;
    private String street;
    private String service;

    public String getCep() {
        return cep;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getStreet() {
        return street;
    }

    public String getService() {
        return service;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public void setService(String service) {
        this.service = service;
    }
}
