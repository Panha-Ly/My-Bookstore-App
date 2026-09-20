package com.lypanha.mybookstore;

import static com.google.android.material.textfield.TextInputLayout.END_ICON_NONE;
import static com.google.android.material.textfield.TextInputLayout.END_ICON_PASSWORD_TOGGLE;
import static com.lypanha.mybookstore.model.User.LOGGED_IN;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.lypanha.mybookstore.databinding.ActivityLoginBinding;
import com.lypanha.mybookstore.model.User;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Action Bar
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
            getSupportActionBar().show();
        }

        binding.edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (binding.edtEmailLayout.getError() != null) {
                    binding.edtEmailLayout.setError(null);
                    binding.edtEmailLayout.setErrorEnabled(false);
                }
            }
        });

        binding.edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (binding.edtPasswordLayout.getError() != null) {
                    binding.edtPasswordLayout.setError(null);
                    binding.edtPasswordLayout.setErrorEnabled(false);
                    binding.edtPasswordLayout.setEndIconMode(END_ICON_PASSWORD_TOGGLE);
                }
            }
        });

        binding.btnLogin.setOnClickListener(v -> {
            boolean emailIsEmpty = Objects.toString(binding.edtEmail.getText(), "").isEmpty();
            boolean passwordIsEmpty = binding.edtPassword.getEditableText().toString().isEmpty();

            if(emailIsEmpty) {
                binding.edtEmailLayout.setErrorEnabled(true);
                binding.edtEmailLayout.setError("Email Empty");
            }

            if(passwordIsEmpty) {
                binding.edtPasswordLayout.setErrorEnabled(true);
                binding.edtPasswordLayout.setError("Password Empty");
                binding.edtPasswordLayout.setEndIconMode(END_ICON_NONE);
            }

            if(!emailIsEmpty && !passwordIsEmpty) {
                boolean loggedIn = false;
                for (User user : Utils.getInstance().getUsers()) {
                    if (user.getEmail().contentEquals(binding.edtEmail.getEditableText()) && user.getPassword().contentEquals(binding.edtPassword.getEditableText())) {
                        user.setLoginStatus(LOGGED_IN);
                        loggedIn = true;
                    }
                }

                if (loggedIn) {
                    Toast.makeText(LoginActivity.this, "Welcome back!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
                    binding.edtEmail.setText("");
                    binding.edtPassword.getEditableText().clear();
                    binding.edtEmail.requestFocus();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}