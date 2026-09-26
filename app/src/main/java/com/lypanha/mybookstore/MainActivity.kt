package com.lypanha.mybookstore

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.lypanha.mybookstore.databinding.ActivityMainBinding
import com.lypanha.mybookstore.databinding.NavHeaderBinding
import com.lypanha.mybookstore.model.User

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Nav Header Views
    private lateinit var navHeaderBinding: NavHeaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        navHeaderBinding = NavHeaderBinding.bind(binding.navView.getHeaderView(0))
        setContentView(binding.root)

        // Toolbar
        binding.toolbar.title = ""
        setSupportActionBar(binding.toolbar)

        // Navigation drawer
        binding.navView.setNavigationItemSelectedListener(object :
            NavigationView.OnNavigationItemSelectedListener {
                override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
                    when (menuItem.itemId) {
                        R.id.nav_home -> {
                            supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.frame_container, HomeFragment())
                                .commit()
                            binding.drawerLayout.closeDrawer(GravityCompat.START)
                            return true
                        }
                        R.id.nav_cart -> {
                            supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.frame_container, CartFragment())
                                .commit()
                            binding.drawerLayout.closeDrawer(GravityCompat.START)
                            return true
                        }
                        R.id.nav_book_series -> {
                            supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.frame_container, BookSeriesFragment())
                                .commit()
                            binding.drawerLayout.closeDrawer(GravityCompat.START)
                            return true
                        }
                        R.id.nav_all_books -> {
                            supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.frame_container, AllBooksFragment())
                                .commit()
                            binding.drawerLayout.closeDrawer(GravityCompat.START)
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
                            binding.drawerLayout.closeDrawer(GravityCompat.START)
                            return true
                        }
                        else -> {
                            binding.drawerLayout.closeDrawer(GravityCompat.START)
                            return true
                        }
                    }
                }
            }
        )

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.open,
            R.string.close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        // add Home fragment when entering activity
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame_container, HomeFragment())
                .commit()
            binding.navView.setCheckedItem(R.id.nav_home)
        }

        // Back button functionality
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                var itemId = -1
                if (binding.navView.checkedItem != null) {
                    itemId = binding.navView.checkedItem!!.itemId
                }

                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                } else if (itemId == R.id.nav_cart || itemId == R.id.nav_all_books || itemId == R.id.nav_book_series || itemId == R.id.nav_settings) {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_container, HomeFragment())
                        .commit()
                    binding.navView.setCheckedItem(R.id.nav_home)
                } else if (itemId == R.id.nav_about_us) {
                    val currentFragment = supportFragmentManager.findFragmentById(R.id.frame_container)
                    if (currentFragment is HomeFragment) {
                        finish()
                    } else {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.frame_container, HomeFragment())
                            .commit()
                        binding.navView.setCheckedItem(R.id.nav_home)
                    }
                } else {
                    finish()
                }
            }
        })

        // Nav Header Views
        navHeaderBinding.btnLogin.setOnClickListener {
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
        }
        navHeaderBinding.btnLogout.setOnClickListener {
            for (user in Utils.users) {
                if (user.isLoginStatus) {
                    user.isLoginStatus = User.LOGGED_OUT
                }
            }
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
            with(navHeaderBinding){
                userProfile.visibility = View.GONE
                btnLogin.visibility = View.VISIBLE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        for (user in Utils.users) {
            if (user.isLoginStatus) {
                with(navHeaderBinding){
                    imgProfile.setImageResource(user.imageId)
                    txtName.text = user.username
                    txtEmail.text = user.email
                    userProfile.visibility = View.VISIBLE
                    btnLogin.visibility = View.GONE
                }
            }
        }
    }
}