package com.example.serviceflexapp.database;

public class Provider {
    public String providerId;
    public String firstName;
    public String lastName;
    public String phoneNumber;
    public String email;
    public String password;
    public String address;
    public int age;
    public String jobChoice;
    public String priceRange;

    public Provider() {
        // Default constructor required for calls to DataSnapshot.getValue(Provider.class)
    }

    public Provider(String providerId, String firstName, String lastName, String phoneNumber, String email, String password, String address, int age, String jobChoice, String priceRange) {
        this.providerId = providerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.address = address;
        this.age = age;
        this.jobChoice = jobChoice;
        this.priceRange = priceRange;
    }
}

