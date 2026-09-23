package com.example.homeshine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class BookingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bookings, container, false);

        LinearLayout container_ = root.findViewById(R.id.bookingsContainer);
        TextView emptyState = root.findViewById(R.id.emptyState);

        List<Booking> bookings = BookingRepository.getInstance().getBookings();

        if (bookings.isEmpty()) {
            emptyState.setVisibility(View.VISIBLE);
        } else {
            emptyState.setVisibility(View.GONE);
            for (Booking b : bookings) {
                View item = inflater.inflate(R.layout.item_booking, container_, false);
                ((TextView) item.findViewById(R.id.bookingIcon)).setText(b.emoji);
                ((TextView) item.findViewById(R.id.bookingTitle)).setText(b.serviceName);
                ((TextView) item.findViewById(R.id.bookingSubtitle)).setText(b.when + " · " + b.cleaner);
                container_.addView(item, container_.indexOfChild(emptyState));
            }
        }

        return root;
    }
}
