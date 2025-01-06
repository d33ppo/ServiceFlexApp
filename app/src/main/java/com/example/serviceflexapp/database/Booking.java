package com.example.serviceflexapp.database;

public class Booking {
    private String consumerId;
    private String bookingDate;
    private String bookingTime;
    private boolean bookingStatus;
    private String firstName;
    private String address;

    public Booking(String consumerId, String bookingDate, String bookingTime, boolean bookingStatus, String firstName, String address) {
        this.consumerId = consumerId;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.bookingStatus = bookingStatus;
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

    public boolean getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(boolean bookingStatus) {
        this.bookingStatus = bookingStatus;
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
}