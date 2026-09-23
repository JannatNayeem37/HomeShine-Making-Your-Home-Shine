package com.example.homeshine;

public class Booking {
    public String serviceName;
    public String emoji;
    public String when;      // Today / Tomorrow / Schedule (date)
    public String cleaner;   // "Any available cleaner", or a name
    public String price;

    public Booking(String serviceName, String emoji, String when, String cleaner, String price) {
        this.serviceName = serviceName;
        this.emoji = emoji;
        this.when = when;
        this.cleaner = cleaner;
        this.price = price;
    }
}
