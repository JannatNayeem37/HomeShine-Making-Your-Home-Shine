package com.example.homeshine;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private TextView profileName, profileEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        profileName = root.findViewById(R.id.profileName);
        profileEmail = root.findViewById(R.id.profileEmail);

        root.findViewById(R.id.btnEditProfile).setOnClickListener(v -> showEditDialog());

        root.findViewById(R.id.rowMyBookings).setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).selectBottomTab(R.id.nav_bookings);
            }
        });

        root.findViewById(R.id.rowPayments).setOnClickListener(v ->
                Toast.makeText(requireContext(), "No payment methods saved yet", Toast.LENGTH_SHORT).show());

        root.findViewById(R.id.rowAddress).setOnClickListener(v ->
                Toast.makeText(requireContext(), "No saved addresses yet", Toast.LENGTH_SHORT).show());

        root.findViewById(R.id.rowSupport).setOnClickListener(v ->
                new AlertDialog.Builder(requireContext())
                        .setTitle("Help & Support")
                        .setMessage("Need help with a booking?\n\n📞 +880 1XXX-XXXXXX\n✉️ support@homeshine.com")
                        .setPositiveButton("OK", null)
                        .show());

        root.findViewById(R.id.btnLogout).setOnClickListener(v ->
                new AlertDialog.Builder(requireContext())
                        .setTitle("Log out?")
                        .setMessage("You'll need to log in again to book a service.")
                        .setPositiveButton("Log Out", (dialog, which) -> {
                            Intent intent = new Intent(requireContext(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        })
                        .setNegativeButton("Cancel", null)
                        .show());

        return root;
    }

    private void showEditDialog() {
        LinearLayout layout = new LinearLayout(requireContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        int pad = dp(20);
        layout.setPadding(pad, pad, pad, 0);

        EditText nameInput = new EditText(requireContext());
        nameInput.setHint("Full name");
        nameInput.setText(profileName.getText());
        layout.addView(nameInput);

        EditText emailInput = new EditText(requireContext());
        emailInput.setHint("Email");
        emailInput.setText(profileEmail.getText());
        layout.addView(emailInput);

        new AlertDialog.Builder(requireContext())
                .setTitle("Edit Profile")
                .setView(layout)
                .setPositiveButton("Save", (dialog, which) -> {
                    if (!TextUtils.isEmpty(nameInput.getText())) profileName.setText(nameInput.getText());
                    if (!TextUtils.isEmpty(emailInput.getText())) profileEmail.setText(emailInput.getText());
                    Toast.makeText(requireContext(), "Profile updated", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private int dp(int value) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(value * density);
    }
}
