package com.example.homeshine;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple in-memory store shared across screens.
 * (Swap this for a real database / API call later.)
 */
public class BookingRepository {

    private static final BookingRepository INSTANCE = new BookingRepository();
    private final List<Booking> bookings = new ArrayList<>();

    private BookingRepository() {}

    public static BookingRepository getInstance() {
        return INSTANCE;
    }

    public void addBooking(Booking booking) {
        bookings.add(0, booking);
    }

    public List<Booking> getBookings() {
        return bookings;
    }
}
