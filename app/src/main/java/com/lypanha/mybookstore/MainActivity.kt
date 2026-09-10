package com.lypanha.mybookstore

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.lypanha.mybookstore.model.User

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    // Nav Header Views
    private lateinit var navHeader: RelativeLayout
    private lateinit var btnLogin: Button
    private lateinit var userProfile: RelativeLayout
    private lateinit var imgProfile: ImageView
    private lateinit var txtName: TextView
    private lateinit var txtEmail: TextView
    private lateinit var btnLogout: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Toolbar
        val toolbar: Toolbar = findViewById<Toolbar>(R.id.toolbar)
            .apply { title = "" }
        setSupportActionBar(toolbar)


        // Navigation drawer
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(object :
            NavigationView.OnNavigationItemSelectedListener {
                override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
                    when (menuItem.itemId) {
                        R.id.nav_home -> {
                            supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.frame_container, HomeFragment())
                                .commit()
                            drawerLayout.closeDrawer(GravityCompat.START)
                            return true
                        }
                        R.id.nav_cart -> {
                            supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.frame_container, CartFragment())
                                .commit()
                            drawerLayout.closeDrawer(GravityCompat.START)
                            return true
                        }
                        R.id.nav_book_series -> {
                            supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.frame_container, BookSeriesFragment())
                                .commit()
                            drawerLayout.closeDrawer(GravityCompat.START)
                            return true
                        }
                        R.id.nav_all_books -> {
                            supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.frame_container, AllBooksFragment())
                                .commit()
                            drawerLayout.closeDrawer(GravityCompat.START)
                            return true
                        }
                        R.id.nav_about_us -> {
                            val messages =
                                "My Bookstore is Designed and Developed by Panha.\n\nApp Version : 1.0\n\nContact Us : panha@mail.com"
                            val builder = AlertDialog.Builder(this@MainActivity)
                            builder.setTitle(getString(R.string.app_name))
                                .setMessage(messages)
                                .setNeutralButton("Dismiss") { dialog, _ ->
                                    dialog.dismiss()
                                }
                                .setIcon(R.drawable.ic_logo)
                                .create()
                                .show()
                            return true
                        }
                        R.id.nav_settings -> {
                            supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.frame_container, SettingsFragment())
                                .commit()
                            drawerLayout.closeDrawer(GravityCompat.START)
                            return true
                        }
                        else -> {
                            drawerLayout.closeDrawer(GravityCompat.START)
                            return true
                        }
                    }
                }
            }
        )

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open,
            R.string.close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        // add Home fragment when entering activity
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame_container, HomeFragment())
                .commit()
            navigationView.setCheckedItem(R.id.nav_home)
        }

        // Back button functionality
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                var itemId = -1
                if (navigationView.checkedItem != null) {
                    itemId = navigationView.checkedItem!!.itemId
                }

                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else if (itemId == R.id.nav_cart || itemId == R.id.nav_all_books || itemId == R.id.nav_book_series || itemId == R.id.nav_settings) {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_container, HomeFragment())
                        .commit()
                    navigationView.setCheckedItem(R.id.nav_home)
                } else if (itemId == R.id.nav_about_us) {
                    val currentFragment = supportFragmentManager.findFragmentById(R.id.frame_container)
                    if (currentFragment is HomeFragment) {
                        finish()
                    } else {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.frame_container, HomeFragment())
                            .commit()
                        navigationView.setCheckedItem(R.id.nav_home)
                    }
                } else {
                    finish()
                }
            }
        })

        // Nav Header Views
        navHeader = navigationView.getHeaderView(0) as RelativeLayout
        btnLogin = navHeader.getChildAt(0) as Button
        userProfile = navHeader.getChildAt(1) as RelativeLayout
        imgProfile = userProfile.getChildAt(0) as ImageView
        txtName = userProfile.getChildAt(2) as TextView
        txtEmail = userProfile.getChildAt(3) as TextView
        btnLogout = userProfile.getChildAt(4) as Button
        btnLogin.setOnClickListener {
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
        }
        btnLogout.setOnClickListener {
            for (user in Utils.users) {
                if (user.isLoginStatus) {
                    user.isLoginStatus = User.LOGGED_OUT
                }
            }
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
            userProfile.visibility = View.GONE
            btnLogin.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        for (user in Utils.users) {
            if (user.isLoginStatus) {
                imgProfile.setImageResource(user.imageId)
                txtName.text = user.username
                txtEmail.text = user.email
                userProfile.visibility = View.VISIBLE
                btnLogin.visibility = View.GONE
            }
        }
    }
}