package com.employeemanagement.model;

import jakarta.persistence.*;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer addressId;

    private String city;

    private String state;

    @Column(name = "pin_code")
    private Integer pinCode;

    public Address() {
    }

    public Address(String city, String state, Integer pinCode) {
        this.city = city;
        this.state = state;
        this.pinCode = pinCode;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getPinCode() {
        return pinCode;
    }

    public void setPinCode(Integer pinCode) {
        this.pinCode = pinCode;
    }

    @Override
    public String toString() {
        return "Address{" + "addressId=" + addressId + ", city='" + city + '\'' + ", state='" + state + '\'' + ", pinCode=" + pinCode + '}';
    }
}