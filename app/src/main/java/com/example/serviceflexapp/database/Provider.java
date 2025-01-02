package com.example.serviceflexapp.database;

public class Provider {
    private String providerId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String address;
    private int age;
    private String priceRange;

    // Default constructor required for calls to DataSnapshot.getValue(Provider.class)
    public Provider() {
    }

    // Constructor with all fields
    public Provider(String providerId, String firstName, String lastName, String phoneNumber, String email, String address, int age, String priceRange) {
        this.providerId = providerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.age = age;
        this.priceRange = priceRange;
    }

    // Getters and setters for all fields
    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }
}