package com.lypanha.mybookstore

import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lypanha.mybookstore.model.User

class LoginActivity : AppCompatActivity() {
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        edtEmail = findViewById(R.id.edtEmail)
        edtPassword = findViewById(R.id.edtPassword)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            if (edtEmail.text.isEmpty() || edtPassword.text.isEmpty()) {
                if (edtEmail.text.isEmpty()) edtEmail.error = "Enter Email"
                if (edtPassword.text.isEmpty()) edtPassword.error = "Enter Password"
            } else {
                var loggedIn = false
                for (user in Utils.users) {
                    if (user.email.contentEquals(edtEmail.text) && user.password.contentEquals(edtPassword.text)) {
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
                    edtEmail.setText("")
                    edtPassword.setText("")
                    edtEmail.requestFocus()
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