package com.example.homeshine;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class BookingActivity extends AppCompatActivity {

    private TextView bkToday, bkTomorrow, bkSchedule;
    private String selectedWhen = "Today";
    private String selectedCleaner = "Any available cleaner";

    private String serviceName, serviceEmoji, servicePrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        serviceName = getIntent().getStringExtra("service_name");
        serviceEmoji = getIntent().getStringExtra("service_emoji");
        servicePrice = getIntent().getStringExtra("service_price");
        if (serviceName == null) serviceName = "Home Cleaning";
        if (serviceEmoji == null) serviceEmoji = "🏠";
        if (servicePrice == null) servicePrice = "from $18";

        String preselectCleaner = getIntent().getStringExtra("preselect_cleaner");
        if (preselectCleaner != null) selectedCleaner = preselectCleaner;

        ((TextView) findViewById(R.id.bookingSvcIcon)).setText(serviceEmoji);
        ((TextView) findViewById(R.id.bookingSvcName)).setText(serviceName);
        ((TextView) findViewById(R.id.bookingSvcPrice)).setText(servicePrice);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        setupWhenButtons();
        setupCleanerRows();

        findViewById(R.id.btnConfirmBooking).setOnClickListener(v -> confirmBooking());
    }

    private void setupWhenButtons() {
        bkToday = findViewById(R.id.bkToday);
        bkTomorrow = findViewById(R.id.bkTomorrow);
        bkSchedule = findViewById(R.id.bkSchedule);

        bkToday.setOnClickListener(v -> selectWhen("Today"));
        bkTomorrow.setOnClickListener(v -> selectWhen("Tomorrow"));
        bkSchedule.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(this, (view, year, month, day) -> {
                String date = (month + 1) + "/" + day + "/" + year;
                selectWhen("Schedule: " + date);
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            dialog.getDatePicker().setMinDate(System.currentTimeMillis());
            dialog.show();
        });
    }

    private void selectWhen(String when) {
        selectedWhen = when;
        boolean today = when.equals("Today");
        boolean tomorrow = when.equals("Tomorrow");
        boolean schedule = when.startsWith("Schedule");

        bkToday.setBackgroundResource(today ? R.drawable.bg_pill_lemon : R.drawable.bg_input_field);
        bkTomorrow.setBackgroundResource(tomorrow ? R.drawable.bg_pill_lemon : R.drawable.bg_input_field);
        bkSchedule.setBackgroundResource(schedule ? R.drawable.bg_pill_lemon : R.drawable.bg_input_field);
        bkSchedule.setText(schedule ? when : "Schedule");
    }

    private void setupCleanerRows() {
        findViewById(R.id.bkCleanerAny).setOnClickListener(v -> selectCleaner("Any available cleaner", R.id.bkCleanerAny));
        findViewById(R.id.bkCleanerRima).setOnClickListener(v -> selectCleaner("Rima Akter", R.id.bkCleanerRima));
        findViewById(R.id.bkCleanerKamal).setOnClickListener(v -> selectCleaner("Kamal Hossain", R.id.bkCleanerKamal));

        if (!selectedCleaner.equals("Any available cleaner")) {
            int id = selectedCleaner.equals("Rima Akter") ? R.id.bkCleanerRima
                    : selectedCleaner.equals("Kamal Hossain") ? R.id.bkCleanerKamal : R.id.bkCleanerAny;
            selectCleaner(selectedCleaner, id);
        }
    }

    private void selectCleaner(String name, int selectedId) {
        selectedCleaner = name;
        int[] ids = {R.id.bkCleanerAny, R.id.bkCleanerRima, R.id.bkCleanerKamal};
        for (int id : ids) {
            findViewById(id).setBackgroundResource(id == selectedId ? R.drawable.bg_card_stroke_selected : R.drawable.bg_card_stroke);
        }
    }

    private void confirmBooking() {
        Booking booking = new Booking(serviceName, serviceEmoji, selectedWhen, selectedCleaner, servicePrice);
        BookingRepository.getInstance().addBooking(booking);
        Toast.makeText(this, "Booking confirmed for " + serviceName + "!", Toast.LENGTH_LONG).show();
        finish();
    }
}
