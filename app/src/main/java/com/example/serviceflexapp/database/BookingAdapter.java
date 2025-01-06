package com.example.serviceflexapp.database;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serviceflexapp.R;

import java.util.List;

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
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView tvFirstName, tvBookingDate, tvBookingTime, tvAddress;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFirstName = itemView.findViewById(R.id.TV_Sender);
            tvBookingDate = itemView.findViewById(R.id.TV_Date);
            tvBookingTime = itemView.findViewById(R.id.TV_Time);
            tvAddress = itemView.findViewById(R.id.TV_AddressUpcomingBooking);
        }
    }
}