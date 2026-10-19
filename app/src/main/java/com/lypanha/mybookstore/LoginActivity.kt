package com.lypanha.mybookstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.lypanha.mybookstore.ui.features.login.LoginScreen
import com.lypanha.mybookstore.ui.theme.MyBookstoreTheme

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyBookstoreTheme {
                LoginScreen()
            }
        }

    }
}