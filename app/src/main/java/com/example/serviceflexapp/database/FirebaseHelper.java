package.com.example.serviceflexapp;
package com.example.serviceflexapp.database;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FirebaseHelper {
    private final FirebaseFirestore db;

    public FirebaseHelper(){
        db = FirebaseFirestore.getInstance();
    }

    //-----USERS-----
    public void createUser(String userID, String email, String name, String passwordHash, String profilePictureURL, String role, long createdAt, OnCompleteListener<Void> listener){
        Map<String, Object> user = new HashMap<>();
        user.put("userID", userID);
        user.put("passwordHash", passwordHash);
        user.put("profilePictureURL", profilePictureURL);
        user.put("role", role);
        user.put("createdAt", createdAt);

        db.collection("Users").document(userID).set(user).addOnCompleteListener(listener);

    }

    //-----SERVICE PROVIDERS-----
    public void createServiceProvider(String serviceProviderID, boolean availability, String[] daysOfTheWeek, String time, long createdAt, Map<String, Object> paymentDetails, double paymentRate, double rating, String[] servicesOffered, String[] specialties, OnCompleteListener<Void> listener){
        Map<String, Object> serviceProvider = new HashMap<>();
        serviceProvider.put("serviceProviderID", serviceProviderID);
        serviceProvider.put("availability", availability);
        serviceProvider.put("daysOfTheWeek", daysOfTheWeek);
        serviceProvider.put("time", time);
        serviceProvider.put("createdAt", createdAt);
        serviceProvider.put("paymentDetails", paymentDetails);
        serviceProvider.put("paymentRate", paymentRate);
        serviceProvider.put("rating", rating);
        serviceProvider.put("servicesOffered", servicesOffered);
        serviceProvider.put("specialties", specialties);

        db.collection("ServiceProviders").document(serviceProviderID).set(serviceProvider).addOnCompleteListener(listener);

    }

    //-----APPOINTMENTS-----
    public void createAppointment(String appointmentID, long createdAt, long scheduledAt, String serviceProviderID, String serviceType, String status, long updateAt,String userID, OnCompleteListener<Void>listener ){
        Map<String, Object> appointment = new HashMap<>();
        appointment.put("appointmentID", appointmentID);
        appointment.put("createdAt", createdAt);
        appointment.put("scheduledAt", scheduledAt);
        appointment.put("serviceProviderID", serviceProviderID);
        appointment.put("serviceType", serviceType);
        appointment.put("status", status);
        appointment.put("updateAt", updateAt);
        appointment.put("userID", userID);

        db.collection("Appointments").document(appointmentID).set(appointment).addOnCompleteListener(listener);
    }

    //-----PAYMENTS-----
    public void createPayment(String paymentID, double amount, String appointmentID, String paymentMethod, String serviceProviderID, String status, long transactionDate, String userID, OnCompleteListener<Void>listener){
        Map<String, Object> payment = new HashMap<>();
        payment.put("paymentID", paymentID);
        payment.put("amount", amount);
        payment.put("appointmentID", appointmentID);
        payment.put("paymentMethod", paymentMethod);
        payment.put("serviceProviderID", serviceProviderID);
        payment.put("status", status);
        payment.put("transactionDate", transactionDate);
        payment.put("userID", userID);

        db.collection("Payments").document(paymentID).set(payment).addOnCompleteListener(listener);
    }

    //-----NOTIFICATIONS-----
    public void createNotification(String notificationID, long createdAt, boolean read, String recipientID1, String recipientID2, String type, OnCompleteListener<Void>listener){
        Map<String, Object> notification = new HashMap<>();
        notification.put("notificationID", notificationID);
        notification.put("createdAt", createdAt);
        notification.put("read", read);
        notification.put("recipientID1", recipientID1);
        notification.put("recipientID2", recipientID2;
        notification.put("type",type);

        db.collection("Notifications").document(notificationID).set(notification).addOnCompleteListener(listener);
    }

    //-----MESSAGES-----
    public void createMessage(String messageID, String content, String senderID, long timestamp, OnCompleteListener<Void>listener){
        Map<String, Object> message = new HashMap<>();
        message.put("messageID", messageID);
        message.put("content", content);
        message.put("senderID", senderID);
        message.put("timestamp", timestamp);

        db.collection("Notifications").document("Messages").collection("Messages").document(messageID).set(message).addOnCompleteListener(listener);
    }
}
