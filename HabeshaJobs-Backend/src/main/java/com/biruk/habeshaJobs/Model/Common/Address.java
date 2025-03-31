package com.biruk.habeshaJobs.Model.Common;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    private String streetAddress;
    private String city;
    private String State;
    private String Country;
    private String zipCode;


    public Address() {

    }

    public Address(String streetAddress, String city, String state, String country, String zipCode) {
        this.streetAddress = streetAddress;
        this.city = city;
        State = state;
        Country = country;
        this.zipCode = zipCode;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }


    @Override
    public String toString() {
        return "Address{" +
                "streetAddress='" + streetAddress + '\'' +
                ", city='" + city + '\'' +
                ", State='" + State + '\'' +
                ", Country='" + Country + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
