package com.lypanha.mybookstore;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

import com.lypanha.mybookstore.model.User;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    // Nav Header Views
    private RelativeLayout navHeader, userProfile;
    private Button btnLogin, btnLogout;

    private TextView txtName, txtEmail;
    private ImageView imgProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        


        // Database instance
        Utils.getInstance();
        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        // Navigation drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        // add Home fragment when entering activity
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        // Back button functionality
        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                int itemId = -1;
                if(navigationView.getCheckedItem() != null){
                    itemId = navigationView.getCheckedItem().getItemId();
                }

                if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if(itemId == R.id.nav_cart || itemId == R.id.nav_all_books || itemId == R.id.nav_book_series || itemId == R.id.nav_settings){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new HomeFragment()).commit();
                    navigationView.setCheckedItem(R.id.nav_home);
                } else if (itemId == R.id.nav_about_us) {
                    Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
                    if(currentFragment instanceof HomeFragment){
                        finish();
                    } else {
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new HomeFragment()).commit();
                        navigationView.setCheckedItem(R.id.nav_home);
                    }
                } else {
                    finish();
                }
            }
        });

        // Nav Header Views
        navHeader = (RelativeLayout) navigationView.getHeaderView(0);
        btnLogin = (Button) navHeader.getChildAt(0);
        userProfile = (RelativeLayout) navHeader.getChildAt(1);
        imgProfile = (ImageView) userProfile.getChildAt(0);
        txtName = (TextView) userProfile.getChildAt(2);
        txtEmail = (TextView) userProfile.getChildAt(3);
        btnLogout = (Button) userProfile.getChildAt(4);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));

            }
        });
        btnLogout.setOnClickListener(v -> {
            for (User user :
                    Utils.getInstance().getUsers()) {
                if(user.isLoginStatus()){
                    user.setLoginStatus(User.LOGGED_OUT);
                }
            }
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
            userProfile.setVisibility(View.GONE);
            btnLogin.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_home){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new HomeFragment()).commit();
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } else if (item.getItemId() == R.id.nav_cart) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new CartFragment()).commit();
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } else if (item.getItemId() == R.id.nav_book_series) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new BookSeriesFragment()).commit();
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } else if(item.getItemId() == R.id.nav_all_books){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new AllBooksFragment()).commit();
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } else if (item.getItemId() == R.id.nav_about_us) {
            String messages = "My Bookstore is Designed and Developed by Panha.\n\nApp Version : 1.0\n\nContact Us : panha@mail.com";
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(getString(R.string.app_name))
                    .setMessage(messages)
                    .setNeutralButton("Dismiss", (dialog, which) -> dialog.dismiss())
                    .setIcon(R.drawable.ic_logo)
                    .create().show();
            return true;
        } else if (item.getItemId() == R.id.nav_settings) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new SettingsFragment()).commit();
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (User user :
                Utils.getInstance().getUsers()) {
            if(user.isLoginStatus()){
                imgProfile.setImageResource(user.getImageId());
                txtName.setText(user.getUsername());
                txtEmail.setText(user.getEmail());
                userProfile.setVisibility(View.VISIBLE);

                btnLogin.setVisibility(View.GONE);

            }
        }
    }
}