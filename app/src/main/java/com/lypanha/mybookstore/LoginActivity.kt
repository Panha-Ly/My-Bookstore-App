package com.lypanha.mybookstore

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lypanha.mybookstore.databinding.ActivityLoginBinding
import com.lypanha.mybookstore.model.User

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(
            binding.main
        ) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnLogin.setOnClickListener {
            if (binding.edtEmail.text.isEmpty() || binding.edtPassword.text.isEmpty()) {
                if (binding.edtEmail.text.isEmpty()) binding.edtEmail.error = "Enter Email"
                if (binding.edtPassword.text.isEmpty()) binding.edtPassword.error = "Enter Password"
            } else {
                var loggedIn = false
                for (user in Utils.users) {
                    if (user.email.contentEquals(binding.edtEmail.text) && user.password.contentEquals(
                            binding.edtPassword.text
                        )
                    ) {
                        user.isLoginStatus = User.LOGGED_IN
                        loggedIn = true
                    }
                }
                if (loggedIn) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Welcome back!",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Incorrect email or password",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.edtEmail.setText("")
                    binding.edtPassword.setText("")
                    binding.edtEmail.requestFocus()
                }
            }
        }

        // Action Bar
        supportActionBar?.let { actionBar ->
            actionBar.displayOptions = ActionBar.DISPLAY_HOME_AS_UP
            actionBar.show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}