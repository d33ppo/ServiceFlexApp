package com.example.serviceflexapp.database;

public class Booking {
    private String consumerId;
    private String bookingDate;
    private String bookingTime;
    private boolean isCompleted;
    private String firstName;
    private String address;
    private String providerId;
    private String category;

    public Booking(String consumerId, String bookingDate, String bookingTime, boolean isCompleted, String firstName, String address) {
        this.consumerId = consumerId;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.isCompleted = isCompleted;
        this.firstName = firstName;
        this.address = address;
    }

    //Constructor for ProviderBookingsPage1Fragment
    public Booking(String bookingDate, String bookingTime, String firstName, String address) {
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.firstName = firstName;
        this.address = address;
    }

    //Constructor for ConsumerCompletedBookings
    public Booking(String providerId, String bookingDate, String bookingTime, String firstName, String address) {
        this.providerId = providerId;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.firstName = firstName;
        this.address = address;
    }

    // Constructor for ConsumerUpcomingBookings
    public Booking(String providerId, String bookingDate, String bookingTime, String firstName, String address, String category) {
        this.providerId = providerId;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.firstName = firstName;
        this.address = address;
        this.category = category;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProviderId() {
        return providerId;
    }

    public String getCategory() {
        return category;
    }
}