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

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link SettingsFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class SettingsFragment extends Fragment {

//    // TOD0: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TOD: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public SettingsFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment SettingsFragment.
//     */
//    // TOD0: Rename and change types and number of parameters
//    public static SettingsFragment newInstance(String param1, String param2) {
//        SettingsFragment fragment = new SettingsFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    private SwitchCompat darkModeSwitch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        darkModeSwitch = view.findViewById(R.id.darkModeSwitch);

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