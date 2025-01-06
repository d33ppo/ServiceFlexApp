package com.example.serviceflexapp.database;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serviceflexapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingAdapterProvider extends RecyclerView.Adapter<BookingAdapterProvider.BookingProviderViewHolder> {

    private List<Booking> bookingList;

    public BookingAdapterProvider(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public BookingProviderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.bookings_cardview_upcoming, parent, false);
            return new BookingProviderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingProviderViewHolder holder, int position) {
        Booking booking = bookingList.get(position);

        holder.tvFirstName.setText(booking.getFirstName());
        holder.tvBookingDate.setText(booking.getBookingDate());
        holder.tvBookingTime.setText(booking.getBookingTime());
        holder.tvAddress.setText(booking.getAddress());

        holder.completed.setOnClickListener( v -> {
            FirebaseFirestore fs = FirebaseFirestore.getInstance();
            fs.collection("providers")
                    .document(FirebaseAuth.getInstance().getUid())
                    .collection("appointment")
                    .whereEqualTo("consumerId",booking.getConsumerId())
                    .whereEqualTo("bookingDate", booking.getBookingDate())
                    .whereEqualTo("bookingTime", booking.getBookingTime())
                    .get()
                    .addOnCompleteListener( task -> {
                        if(task.isSuccessful()){
                            // Get the first matching document
                            DocumentSnapshot document = task.getResult().getDocuments().get(0);
                            String appointmentId = document.getId();

                            Map<String, Object> setCompleted = new HashMap<>();
                            setCompleted.put("bookingDate", booking.getBookingDate());
                            setCompleted.put("bookingTime", booking.getBookingTime());
                            setCompleted.put("consumerId", booking.getConsumerId());
                            setCompleted.put("isCompleted", true);
                            fs.collection("providers")
                                    .document(FirebaseAuth.getInstance().getUid())
                                    .collection("appointment")
                                    .document(appointmentId)
                                    .set(setCompleted);
                        }
                    });
        });
    }

    /*private void completeAppointment() {
        FirebaseFirestore fs = FirebaseFirestore.getInstance();
        fs.collection("providers")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("appointment")
                .whereEqualTo("consumerId",bookingList.get(onBindViewHolder();));
    }*/

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    static class BookingProviderViewHolder extends RecyclerView.ViewHolder {
        TextView tvFirstName, tvBookingDate, tvBookingTime, tvAddress;

        Button completed;

        public BookingProviderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFirstName = itemView.findViewById(R.id.TV_Sender);
            tvBookingDate = itemView.findViewById(R.id.TV_Date);
            tvBookingTime = itemView.findViewById(R.id.TV_Time);
            tvAddress = itemView.findViewById(R.id.TV_AddressUpcomingBooking);
            completed = itemView.findViewById(R.id.BTN_Completed);
        }
    }
}