package com.example.homeshine;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        // Default tab
        showFragment(new HomeFragment());

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                showFragment(new HomeFragment());
                return true;
            } else if (id == R.id.nav_bookings) {
                showFragment(new BookingsFragment());
                return true;
            } else if (id == R.id.nav_services) {
                showFragment(new ServicesFragment());
                return true;
            } else if (id == R.id.nav_profile) {
                showFragment(new ProfileFragment());
                return true;
            }
            return false;
        });
    }

    /** Called by HomeFragment's "See all" links to jump to other tabs. */
    public void selectBottomTab(int menuItemId) {
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(menuItemId);
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        tx.replace(R.id.fragmentContainer, fragment);
        tx.commit();
    }
}
