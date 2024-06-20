package com.lypanha.mybookstore;

import static com.lypanha.mybookstore.model.User.LOGGED_IN;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.lypanha.mybookstore.model.User;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtEmail.getText().toString().isEmpty() && edtPassword.getText().toString().isEmpty()){
                    edtEmail.setError("Enter Email");
                    edtPassword.setError("Enter Password");
                    return;
                } else if (edtEmail.getText().toString().isEmpty()) {
                    edtEmail.setError("Enter Email");
                } else if (edtPassword.getText().toString().isEmpty()) {
                    edtPassword.setError("Enter Password");
                } else {
                    boolean loggedIn = false;
                    for (User user :
                            Utils.getInstance().getUsers()) {
                        if (user.getEmail().contentEquals(edtEmail.getText()) && user.getPassword().contentEquals(edtPassword.getText())) {
                            user.setLoginStatus(LOGGED_IN);
                            loggedIn = true;
                        }
                    }

                    if (loggedIn) {
                        Toast.makeText(LoginActivity.this, "Welcome back!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
                        edtEmail.setText("");
                        edtPassword.setText("");
                        edtEmail.requestFocus();
                    }
                }
            }
        });

        // Action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
            getSupportActionBar().show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}