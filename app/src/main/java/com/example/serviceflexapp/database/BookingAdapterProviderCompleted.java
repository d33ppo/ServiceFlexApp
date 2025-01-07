package com.example.serviceflexapp.database;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serviceflexapp.R;

import java.util.List;

public class BookingAdapterProviderCompleted extends RecyclerView.Adapter<BookingAdapterProviderCompleted.BookingProviderCompletedViewHolder> {

    private List<Booking> bookingList;

    public BookingAdapterProviderCompleted(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public BookingProviderCompletedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bookings_cardview_completed, parent, false);
        return new BookingProviderCompletedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingProviderCompletedViewHolder holder, int position) {
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

    static class BookingProviderCompletedViewHolder extends RecyclerView.ViewHolder {
        TextView tvFirstName, tvBookingDate, tvBookingTime, tvAddress;

        public BookingProviderCompletedViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFirstName = itemView.findViewById(R.id.TV_Sender);
            tvBookingDate = itemView.findViewById(R.id.TV_Date);
            tvBookingTime = itemView.findViewById(R.id.TV_Time);
            tvAddress = itemView.findViewById(R.id.TV_AddressCompletedBooking);
        }
    }
}