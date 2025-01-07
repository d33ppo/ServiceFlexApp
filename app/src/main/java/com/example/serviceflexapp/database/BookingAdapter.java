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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private List<Booking> bookingList;

    public BookingAdapter(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bookings_cardview_upcoming, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookingList.get(position);

        holder.tvFirstName.setText(booking.getFirstName());
        holder.tvBookingDate.setText(booking.getBookingDate());
        holder.tvBookingTime.setText(booking.getBookingTime());
        holder.tvAddress.setText(booking.getAddress());

        holder.completed.setOnClickListener( v -> {
            FirebaseFirestore fs = FirebaseFirestore.getInstance();
            fs.collection("consumers")
                    .document(FirebaseAuth.getInstance().getUid())
                    .collection("appointment")
                    .whereEqualTo("providerId",booking.getProviderId())
                    .whereEqualTo("bookingDate", booking.getBookingDate())
                    .whereEqualTo("bookingTime", booking.getBookingTime())
                    .whereEqualTo("category", booking.getCategory())
                    .whereEqualTo("isCompleted", false)
                    .get()
                    .addOnCompleteListener( task -> {
                        if(task.isSuccessful()){
                            // Get the first matching document
                            DocumentSnapshot document = task.getResult().getDocuments().get(0);
                            String appointmentId = document.getId();

                            Map<String, Object> setCompleted = new HashMap<>();
                            setCompleted.put("bookingDate", booking.getBookingDate());
                            setCompleted.put("bookingTime", booking.getBookingTime());
                            setCompleted.put("providerId", booking.getProviderId());
                            setCompleted.put("category", booking.getCategory());
                            setCompleted.put("isCompleted", true);
                            fs.collection("consumers")
                                    .document(FirebaseAuth.getInstance().getUid())
                                    .collection("appointment")
                                    .document(appointmentId)
                                    .set(setCompleted);
                        }
                    });
        });
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView tvFirstName, tvBookingDate, tvBookingTime, tvAddress;
        Button completed;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFirstName = itemView.findViewById(R.id.TV_Sender);
            tvBookingDate = itemView.findViewById(R.id.TV_Date);
            tvBookingTime = itemView.findViewById(R.id.TV_Time);
            tvAddress = itemView.findViewById(R.id.TV_AddressUpcomingBooking);
            completed = itemView.findViewById(R.id.BTN_Completed);
        }
    }
}