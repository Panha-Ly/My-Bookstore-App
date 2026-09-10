package com.lypanha.mybookstore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val darkModeSwitch: SwitchCompat = view.findViewById(R.id.darkModeSwitch)


        darkModeSwitch.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            // Handle the switch state (isChecked)
            if (isChecked) {
                // Enable dark mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                darkModeSwitch.setText(R.string.text_on)
            } else {
                // Disable dark mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                darkModeSwitch.setText(R.string.text_off)
            }
        }


        // Check if night mode is on when user comes back to fragment
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            darkModeSwitch.setText(R.string.text_on)
            darkModeSwitch.setChecked(true)
        }
    }
}