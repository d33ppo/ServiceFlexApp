package com.example.serviceflexapp.database;

public class Consumer {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private String address;

    public Consumer() {
    }

    //Constructor for ConsumerRegistrationActivity
    public Consumer(String firstName, String lastName, String phoneNumber, String email, String password, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.address = address;
    }

    // Getters and setters for all fields
    public String getFirstName() {return firstName;}

    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}

    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getPhoneNumber() {return phoneNumber;}

    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}

    public  void setPassword(String password) {this.password = password;}

    public String getAddress() {return address;}

    public void setAddress(String address) {this.address = address;}
}
