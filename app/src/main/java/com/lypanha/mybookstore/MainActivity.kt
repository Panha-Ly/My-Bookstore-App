package com.lypanha.mybookstore

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.lypanha.mybookstore.databinding.ActivityMainBinding
import com.lypanha.mybookstore.databinding.NavHeaderBinding
import com.lypanha.mybookstore.model.User

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Nav Header Views
    private lateinit var navHeaderBinding: NavHeaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Add Home fragment when entering activity
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_container, HomeFragment())
                .commit()
            binding.navView.setCheckedItem(R.id.nav_home)
        }

        // Toolbar
        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }

        // Navigation drawer
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            val itemId = menuItem.itemId
            // 1. Handle special non-fragment items first
            if (itemId == R.id.nav_about_us) {
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.app_name))
                    .setMessage("My Bookstore is Designed and Developed by Panha.\n\nApp Version : 1.0\n\nContact Us : panha@mail.com")
                    .setNeutralButton("Dismiss") { dialog, _ -> dialog.dismiss() }
                    .setIcon(R.mipmap.ic_launcher)
                    .create().show()
                binding.drawerLayout.close()
                return@setNavigationItemSelectedListener false // don't check About Us item
            }
            // 2. Clear the backstack if navigating explicitly back to Home
            // This prevents building an endless history of fragments
            if (itemId == R.id.nav_home) {
                supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_container, HomeFragment())
                    .commit()
                binding.drawerLayout.close()
                return@setNavigationItemSelectedListener true
            }
            // 3. For all other side fragments, replace the fragment AND add to backstack
            val targetFragment: Fragment? = when (itemId) {
                R.id.nav_cart -> CartFragment()
                R.id.nav_book_series -> BookSeriesFragment()
                R.id.nav_all_books -> AllBooksFragment()
                R.id.nav_settings -> SettingsFragment()
                else -> null
            }
            if (targetFragment != null) {
                // Clear previous side-fragments from backstack so pressing back always goes directly to Home
                supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_container, targetFragment)
                    .addToBackStack(null) // <--- Adds only side fragments to the stack!
                    .commit()
            }
            binding.drawerLayout.close()
            true
        }

        // Back button functionality
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // 1. If drawer is open, close it
                if (binding.drawerLayout.isOpen) {
                    binding.drawerLayout.close()
                    return
                }
                // 2. If we are on a side fragment, pop back to HomeFragment
                if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack()
                    binding.navView.setCheckedItem(R.id.nav_home) // Reset selection UI
                    return
                }
                // 3. If we are already on HomeFragment (stack is empty), exit the app
                finish()
            }
        })

        // Nav Header Views
        navHeaderBinding = NavHeaderBinding.bind(binding.navView.getHeaderView(0))
        navHeaderBinding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        navHeaderBinding.btnLogout.setOnClickListener {
            Utils.users.forEach { user ->
                if (user.isLoginStatus) user.isLoginStatus = User.LOGGED_OUT
            }
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
            navHeaderBinding.userProfile.visibility = View.GONE
            navHeaderBinding.btnLogin.visibility = View.VISIBLE
        }

    }

    override fun onResume() {
        super.onResume()
        Utils.users.forEach { user ->
            if (user.isLoginStatus) {
                navHeaderBinding.imgProfile.setImageResource(user.imageId)
                navHeaderBinding.txtName.text = user.username
                navHeaderBinding.txtEmail.text = user.email

                navHeaderBinding.userProfile.visibility = View.VISIBLE
                navHeaderBinding.btnLogin.visibility = View.GONE
            }
        }
    }
}