package com.lypanha.mybookstore

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import com.lypanha.mybookstore.ui.features.bookdetails.BookDetailsScreen
import com.lypanha.mybookstore.ui.theme.MyBookstoreTheme

class BookActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyBookstoreTheme {
                BookDetailsScreen()
            }
        }

        // Status bar and Navigation bar
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = false // false = white icons
            isAppearanceLightNavigationBars = false
            Log.d("", "Hi " + Build.VERSION.SDK_INT)
        }

    }
}