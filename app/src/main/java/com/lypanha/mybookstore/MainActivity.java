package com.lypanha.mybookstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.lypanha.mybookstore.databinding.ActivityMainBinding;
import com.lypanha.mybookstore.databinding.NavHeaderBinding;
import com.lypanha.mybookstore.model.User;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    // Nav Header Views
    private NavHeaderBinding navHeaderBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Add Home fragment when entering activity
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_container, new HomeFragment())
                    .commit();
            binding.navView.setCheckedItem(R.id.nav_home);
        }

        // Database instance
        Utils.getInstance();
        // Toolbar
        binding.toolbar.setTitle("");
        setSupportActionBar(binding.toolbar);

        // Navigation drawer
        binding.navView.setNavigationItemSelectedListener(menuItem -> {
            int itemId = menuItem.getItemId();

            // 1. Handle special non-fragment items first
            if (itemId == R.id.nav_about_us) {
                String messages = "My Bookstore is Designed and Developed by Panha.\n\nApp Version : 1.0\n\nContact Us : panha@mail.com";
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(getString(R.string.app_name))
                        .setMessage(messages)
                        .setNeutralButton("Dismiss", (dialog, which) -> dialog.dismiss())
                        .setIcon(R.drawable.ic_logo)
                        .create().show();
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                return false; // don't check About Us item
            }

            // 2. Clear the backstack if navigating explicitly back to Home
            // This prevents building an endless history of fragments
            if (itemId == R.id.nav_home) {
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, new HomeFragment())
                        .commit();

                binding.drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }

            // 3. For all other side fragments, replace the fragment AND add to backstack
            Fragment targetFragment = null;
            if (itemId == R.id.nav_cart) {
                targetFragment = new CartFragment();
            } else if (itemId == R.id.nav_book_series) {
                targetFragment = new BookSeriesFragment();
            } else if (itemId == R.id.nav_all_books) {
                targetFragment = new AllBooksFragment();
            } else if (itemId == R.id.nav_settings) {
                targetFragment = new SettingsFragment();
            }

            if (targetFragment != null) {
                // Clear previous side-fragments from backstack so pressing back always goes directly to Home
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, targetFragment)
                        .addToBackStack(null) // <--- Adds only side fragments to the stack!
                        .commit();
            }

            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        // Add Hamburger menu icon to the toolbar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.open, R.string.close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Back button functionality
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // 1. If drawer is open, close it
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                    return;
                }

                // 2. If we are on a side fragment, pop back to HomeFragment
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                    binding.navView.setCheckedItem(R.id.nav_home); // Reset selection UI
                    return;
                }

                // 3. If we are already on HomeFragment (stack is empty), exit the app
                finish();
            }
        });

//         Nav Header Views
        navHeaderBinding = NavHeaderBinding.bind(binding.navView.getHeaderView(0));
        navHeaderBinding.btnLogin.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LoginActivity.class)));
        navHeaderBinding.btnLogout.setOnClickListener(v -> {
            Utils.getInstance().getUsers().forEach(user -> {
                if (user.isLoginStatus()) {
                    user.setLoginStatus(User.LOGGED_OUT);
                }
            });
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
            navHeaderBinding.userProfile.setVisibility(View.GONE);
            navHeaderBinding.btnLogin.setVisibility(View.VISIBLE);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.getInstance().getUsers().forEach(user -> {
            if (user.isLoginStatus()) {
                navHeaderBinding.imgProfile.setImageResource(user.getImageId());
                navHeaderBinding.txtName.setText(user.getUsername());
                navHeaderBinding.txtEmail.setText(user.getEmail());

                navHeaderBinding.userProfile.setVisibility(View.VISIBLE);
                navHeaderBinding.btnLogin.setVisibility(View.GONE);
            }
        });
    }
}