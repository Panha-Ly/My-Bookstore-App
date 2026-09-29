package com.lypanha.mybookstore

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.lypanha.mybookstore.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Clear out listener first to avoid unexpected trigger loops
        binding.darkModeSwitch.setOnCheckedChangeListener(null)

        // Check if night mode is on when user comes back to fragment
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
//        Log.d("currentNightMode", currentNightMode.toString())
        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            binding.darkModeSwitch.apply {
                setText(R.string.text_on)
                setChecked(true)
            }
        }

        binding.darkModeSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            // Handle the switch state (isChecked)
            Log.d("","check = $isChecked")
            if (isChecked) {
                // Enable dark mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                buttonView.setText(R.string.text_on)
            } else {
                // Disable dark mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                buttonView.setText(R.string.text_off)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}