package com.lypanha.mybookstore

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout
import com.lypanha.mybookstore.databinding.ActivityLoginBinding
import com.lypanha.mybookstore.model.User
import java.util.Objects

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Toolbar
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (binding.edtEmailLayout.error != null) {
                    binding.edtEmailLayout.apply {
                        error = null
                        isErrorEnabled = false
                    }
                }
            }
        })

        binding.edtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (binding.edtPasswordLayout.error != null) {
                    binding.edtPasswordLayout.apply {
                        error = null
                        isErrorEnabled = false
                        endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
                    }
                }
            }
        })

        binding.btnLogin.setOnClickListener {
            val emailIsEmpty = Objects.toString(binding.edtEmail.getText(), "").isEmpty()
            val passwordIsEmpty = binding.edtPassword.editableText.toString().isEmpty()
            if (emailIsEmpty) {
                binding.edtEmailLayout.apply {
                    error = "Email Empty"
                    isErrorEnabled = true
                }
            }
            if (passwordIsEmpty) {
                binding.edtPasswordLayout.apply {
                    error = "Password Empty"
                    isErrorEnabled = true
                    endIconMode = TextInputLayout.END_ICON_NONE
                }
            }
            if (!emailIsEmpty && !passwordIsEmpty) {
                var loggedIn = false
                for (user in Utils.users) {
                    if (user.email.contentEquals(binding.edtEmail.editableText)
                        && user.password.contentEquals(binding.edtPassword.editableText)
                    ) {
                        user.isLoginStatus = User.LOGGED_IN
                        loggedIn = true
                    }
                }
                if (loggedIn) {
                    Toast.makeText(this@LoginActivity, "Welcome back!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Incorrect email or password",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.edtPassword.editableText.clear()
                    binding.edtEmail.apply {
                        setText("")
                        requestFocus()
                    }
                }
            }
        }

    }
}