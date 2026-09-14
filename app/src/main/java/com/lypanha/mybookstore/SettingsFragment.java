package com.lypanha.mybookstore;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SettingsFragment extends Fragment {

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SwitchCompat darkModeSwitch = view.findViewById(R.id.darkModeSwitch);
        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Handle the switch state (isChecked)
            if (isChecked) {
                // Enable dark mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                darkModeSwitch.setText(R.string.text_on);
            } else {
                // Disable dark mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                darkModeSwitch.setText(R.string.text_off);

            }
        });

        // Check if night mode is on when user comes back to fragment
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            darkModeSwitch.setText(R.string.text_on);
            darkModeSwitch.setChecked(true);
        }

    }

}