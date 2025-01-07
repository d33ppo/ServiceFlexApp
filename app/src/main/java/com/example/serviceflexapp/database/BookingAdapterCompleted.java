package com.example.serviceflexapp.database;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serviceflexapp.R;

import java.util.List;

public class BookingAdapterCompleted extends RecyclerView.Adapter<BookingAdapterCompleted.BookingCompletedViewHolder> {

    private List<Booking> bookingList;

    public BookingAdapterCompleted(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public BookingCompletedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bookings_cardview_completed, parent, false);
        return new BookingCompletedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingCompletedViewHolder holder, int position) {
        Booking booking = bookingList.get(position);

        holder.tvFirstName.setText(booking.getFirstName());
        holder.tvBookingDate.setText(booking.getBookingDate());
        holder.tvBookingTime.setText(booking.getBookingTime());
        holder.tvAddress.setText(booking.getAddress());
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    static class BookingCompletedViewHolder extends RecyclerView.ViewHolder {
        TextView tvFirstName, tvBookingDate, tvBookingTime, tvAddress;

        public BookingCompletedViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFirstName = itemView.findViewById(R.id.TV_Sender);
            tvBookingDate = itemView.findViewById(R.id.TV_Date);
            tvBookingTime = itemView.findViewById(R.id.TV_Time);
            tvAddress = itemView.findViewById(R.id.TV_AddressCompletedBooking);
        }
    }
}