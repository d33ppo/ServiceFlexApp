package com.example.serviceflexapp.database;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;

public class FirebaseManager {

    private static DatabaseReference databaseReference;

    
    public static void initialize() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }
    
    //User Registration
    public void registerUser(String userID, String name, String email, String phone){
        HashMap<String, Object> userData = new HashMap<>();
        userData.put("name", name);
        userData.put("email", email);
        userData.put("phone", phone);
        
        databaseReference.child("users").child(userID).setValue(userData);
    }

    //Service Provider Registration
    public void registerServiceProvider(String providerID, String name, String specialty, String rate, String rating, String yearsOfExperience, String qualifications, String area){
        HashMap<String, Object> providerData = new HashMap<>();
        providerData.put("name", name);
        providerData.put("specialty", specialty);
        providerData.put("rate", rate);
        providerData.put("rating", rating);
        providerData.put("yearsOfExperience", yearsOfExperience);
        providerData.put("qualifications", qualifications);
        providerData.put("area", area);

        databaseReference.child("ServiceProviders").child(providerID).setValue(providerData);
    }

    //Book A Service
    public void bookService(String bookingID, String userID, String providerID, String date, String time){
        HashMap<String, Object> bookingData = new HashMap<>();
        bookingData.put("userID", userID);
        bookingData.put("providerID", providerID);
        bookingData.put("date", date);
        bookingData.put("time", time);

        databaseReference.child("Bookings").child(bookingID).setValue(bookingData);
    }

    //Add Payment
    public void addPayment(String paymentID, String bookingID, String amount, String status){
        HashMap<String, Object> paymentData = new HashMap<>();
        paymentData.put("bookingID", bookingID);
        paymentData.put("amount", amount);
        paymentData.put("status", status);

        databaseReference.child("Payments").child(paymentID).setValue(paymentData);

    }

    //Send Notification
    public void sendNotification(String notificationID, String userID, String message, String timestamp){
        HashMap<String, Object> notificationData = new HashMap<>();
        notificationData.put("userID", userID);
        notificationData.put("message", message);
        notificationData.put("timestamp", System.currentTimeMillis());

        databaseReference.child("Notifications").child(notificationID).setValue(notificationData);

    }
}
