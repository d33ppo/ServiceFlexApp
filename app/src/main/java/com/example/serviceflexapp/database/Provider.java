package com.example.serviceflexapp.database;

import java.util.List;

public class Provider {
    private String providerId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String address;
    private int age;
    private String priceRange;
    private String imageURL;
    private String yearsOfExperience;
    private String rating;
    private String qualifications;
    private List<String> availability; // String array for availability (e.g., ["Monday", "Wednesday", "Friday"])

    // Default constructor required for calls to DataSnapshot.getValue(Provider.class)
    public Provider() {
    }

    // Constructor with all fields
    public Provider(String providerId, String firstName, String lastName, String phoneNumber, String email, String address, int age, String priceRange, String imageURL, String yearsOfExperience, String rating, String qualifications, List<String> availability) {
        this.providerId = providerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.age = age;
        this.priceRange = priceRange;
        this.imageURL = imageURL;
        this.yearsOfExperience = yearsOfExperience;
        this.rating = rating;
        this.qualifications = qualifications;
        this.availability = availability;
    }

    //Constructor for ProviderRegistrationPage2Fragment
    public Provider(String providerId, String firstName, String lastName, String phoneNumber, String email, String address, int age, String priceRange, String qualifications, List<String> availability) {
        this.providerId = providerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.age = age;
        this.priceRange = priceRange;
        this.qualifications = qualifications;
        this.availability = availability;
    }

    //Constructor for Consumer Home Fragment2
    public Provider(String firstName, String priceRange, String imageURL, String rating, String yearsOfExperience) {
        this.firstName = firstName;
        this.priceRange = priceRange;
        this.imageURL = imageURL;
        this.rating = rating;
        this.yearsOfExperience = yearsOfExperience;
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

    public String getImageURL() {return imageURL;}

    public void setImageURL(String imageURL) {this.imageURL = imageURL;}

    public String getYearsOfExperience() {return yearsOfExperience;}

    public void setYearsOfExperience(String yearsOfExperience) {this.yearsOfExperience = yearsOfExperience;}

    public String getRating() {return rating;}

    public void setRating(String rating) {this.rating = rating;}

    public String getQualifications() {return qualifications;}

    public void setQualifications(String qualifications) {this.qualifications = qualifications;}

    public List<String> getAvailability() {return availability;}

    public void setAvailability(List<String> availability) {this.availability = availability;}
}