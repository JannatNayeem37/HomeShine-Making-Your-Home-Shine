package com.example.homeshine;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class HomeFragment extends Fragment {

    private TextView whenToday, whenTomorrow, whenSchedule, estimateValue;
    private String selectedWhen = "Today";
    private String scheduledDate = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        setupSearch(root);
        setupSlider(root);
        setupHero(root);
        setupServiceGrid(root);
        setupWhenPills(root);
        setupCleaners(root);
        setupPromo(root);

        root.findViewById(R.id.btnNotif).setOnClickListener(v ->
                new AlertDialog.Builder(requireContext())
                        .setTitle("Notifications")
                        .setMessage("• Your booking with Rima Akter is confirmed for today\n• 20% off your next booking — use SHINE20")
                        .setPositiveButton("OK", null)
                        .show());

        return root;
    }

    private void setupSearch(View root) {
        EditText search = root.findViewById(R.id.searchBox);
        search.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                String q = search.getText().toString().trim();
                if (!TextUtils.isEmpty(q)) {
                    Toast.makeText(requireContext(), "Searching for \"" + q + "\"…", Toast.LENGTH_SHORT).show();
                    openBooking(capitalize(q), "🔍", "from $18");
                }
                return true;
            }
            return false;
        });
    }

    private void setupSlider(View root) {
        FrameLayout sliderFrame = root.findViewById(R.id.sliderFrame);
        FrameLayout afterPanel = root.findViewById(R.id.afterPanel);
        SeekBar seek = root.findViewById(R.id.beforeAfterSeek);

        sliderFrame.post(() -> {
            int totalWidth = sliderFrame.getWidth();
            updateAfterWidth(afterPanel, totalWidth, seek.getProgress());

            seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    updateAfterWidth(afterPanel, totalWidth, progress);
                }
                @Override public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override public void onStopTrackingTouch(SeekBar seekBar) {}
            });
        });
    }

    private void updateAfterWidth(FrameLayout afterPanel, int totalWidth, int progress) {
        if (totalWidth <= 0) return;
        int revealedWidth = totalWidth - (totalWidth * progress / 100); // drag right hides "after"
        ViewGroup.LayoutParams lp = afterPanel.getLayoutParams();
        lp.width = Math.max(revealedWidth, 0);
        afterPanel.setLayoutParams(lp);
    }

    private void setupHero(View root) {
        root.findViewById(R.id.btnBookNow).setOnClickListener(v ->
                openBooking("Home Cleaning", "🏠", "from $18"));

        root.findViewById(R.id.btnSeePrices).setOnClickListener(v ->
                new AlertDialog.Builder(requireContext())
                        .setTitle("Starting prices")
                        .setMessage("🏠 Home Cleaning — from $18\n🍳 Kitchen — from $12\n🚿 Bathroom — from $10\n✨ Deep Cleaning — from $35\n🛋️ Sofa & Carpet — from $22")
                        .setPositiveButton("Close", null)
                        .show());
    }

    private void setupServiceGrid(View root) {
        root.findViewById(R.id.svcHomeCleaning).setOnClickListener(v -> openBooking("Home Cleaning", "🏠", "from $18"));
        root.findViewById(R.id.svcKitchen).setOnClickListener(v -> openBooking("Kitchen", "🍳", "from $12"));
        root.findViewById(R.id.svcBathroom).setOnClickListener(v -> openBooking("Bathroom", "🚿", "from $10"));
        root.findViewById(R.id.svcDeepCleaning).setOnClickListener(v -> openBooking("Deep Cleaning", "✨", "from $35"));
        root.findViewById(R.id.svcSofaCarpet).setOnClickListener(v -> openBooking("Sofa & Carpet", "🛋️", "from $22"));

        root.findViewById(R.id.svcMore).setOnClickListener(v ->
                new AlertDialog.Builder(requireContext())
                        .setTitle("More services")
                        .setItems(new String[]{"🧺 Laundry", "🌿 Gardening", "🐜 Pest Control", "🎨 Painting", "❄️ AC Service"},
                                (dialog, which) -> {
                                    String[] emojis = {"🧺", "🌿", "🐜", "🎨", "❄️"};
                                    String[] names = {"Laundry", "Gardening", "Pest Control", "Painting", "AC Service"};
                                    openBooking(names[which], emojis[which], "from $15");
                                })
                        .show());

        root.findViewById(R.id.seeAllServices).setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).selectBottomTab(R.id.nav_services);
            }
        });
    }

    private void setupWhenPills(View root) {
        whenToday = root.findViewById(R.id.whenToday);
        whenTomorrow = root.findViewById(R.id.whenTomorrow);
        whenSchedule = root.findViewById(R.id.whenSchedule);
        estimateValue = root.findViewById(R.id.estimateValue);

        whenToday.setOnClickListener(v -> selectWhen("Today"));
        whenTomorrow.setOnClickListener(v -> selectWhen("Tomorrow"));
        whenSchedule.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(requireContext(), (view, year, month, day) -> {
                scheduledDate = (month + 1) + "/" + day + "/" + year;
                selectWhen("Schedule (" + scheduledDate + ")");
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            dialog.getDatePicker().setMinDate(System.currentTimeMillis());
            dialog.show();
        });
    }

    private void selectWhen(String when) {
        selectedWhen = when;
        whenToday.setBackgroundResource(when.equals("Today") ? R.drawable.bg_pill_lemon : R.drawable.bg_pill_teal);
        whenToday.setTextColor(getResources().getColor(when.equals("Today") ? R.color.ink : R.color.white));

        whenTomorrow.setBackgroundResource(when.equals("Tomorrow") ? R.drawable.bg_pill_lemon : R.drawable.bg_pill_teal);
        whenTomorrow.setTextColor(getResources().getColor(when.equals("Tomorrow") ? R.color.ink : R.color.white));

        boolean isSchedule = when.startsWith("Schedule");
        whenSchedule.setBackgroundResource(isSchedule ? R.drawable.bg_pill_lemon : R.drawable.bg_pill_teal);
        whenSchedule.setTextColor(getResources().getColor(isSchedule ? R.color.ink : R.color.white));
        if (isSchedule) whenSchedule.setText(when);
        else whenSchedule.setText("Schedule");

        estimateValue.setText(when.equals("Today") ? "from $18" : "from $15");
    }

    private void setupCleaners(View root) {
        root.findViewById(R.id.cleanerRima).setOnClickListener(v -> showCleanerDetail("Rima Akter", "4.9", "200TK/hr"));
        root.findViewById(R.id.cleanerKamal).setOnClickListener(v -> showCleanerDetail("Kamal Hossain", "4.8", "300TK/hr"));
        root.findViewById(R.id.cleanerShirin).setOnClickListener(v -> showCleanerDetail("Shirin Sultana", "4.7", "350TK/hr"));

        root.findViewById(R.id.seeAllCleaners).setOnClickListener(v ->
                new AlertDialog.Builder(requireContext())
                        .setTitle("Top-rated cleaners")
                        .setItems(new String[]{
                                "Rima Akter — ⭐4.9 — 200TK/hr",
                                "Kamal Hossain — ⭐4.8 — 300TK/hr",
                                "Shirin Sultana — ⭐4.7 — 350TK/hr",
                                "Nasrin Begum — ⭐4.6 — 220TK/hr",
                                "Abdul Karim — ⭐4.5 — 250TK/hr"},
                                (dialog, which) -> Toast.makeText(requireContext(), "Opening profile…", Toast.LENGTH_SHORT).show())
                        .show());
    }

    private void showCleanerDetail(String name, String rating, String rate) {
        new AlertDialog.Builder(requireContext())
                .setTitle(name)
                .setMessage("⭐ " + rating + " rating\n" + rate + "\nBackground-checked · 200+ jobs completed")
                .setPositiveButton("Book " + name.split(" ")[0], (dialog, which) -> openBookingWithCleaner("Home Cleaning", "🏠", "from $18", name))
                .setNegativeButton("Close", null)
                .show();
    }

    private void setupPromo(View root) {
        root.findViewById(R.id.btnPromoCode).setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setPrimaryClip(ClipData.newPlainText("promo", "SHINE20"));
            Toast.makeText(requireContext(), "Code SHINE20 copied!", Toast.LENGTH_SHORT).show();
        });
    }

    private void openBooking(String service, String emoji, String price) {
        openBookingWithCleaner(service, emoji, price, null);
    }

    private void openBookingWithCleaner(String service, String emoji, String price, @Nullable String cleaner) {
        Intent intent = new Intent(requireContext(), BookingActivity.class);
        intent.putExtra("service_name", service);
        intent.putExtra("service_emoji", emoji);
        intent.putExtra("service_price", price);
        if (cleaner != null) intent.putExtra("preselect_cleaner", cleaner);
        startActivity(intent);
    }

    private String capitalize(String s) {
        if (TextUtils.isEmpty(s)) return s;
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}
