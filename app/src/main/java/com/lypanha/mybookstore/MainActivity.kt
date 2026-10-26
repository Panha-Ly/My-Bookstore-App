package com.lypanha.mybookstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Surface
import com.lypanha.mybookstore.ui.theme.MyBookstoreTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Determine the actual UI rendering color mode for the local Compose wrapper
            val darkTheme = when (Utils.appThemeMode) {
                Utils.THEME_DARK -> true
                Utils.THEME_LIGHT -> false
                else -> isSystemInDarkTheme() // If "system", follow OS directly
            }
            MyBookstoreTheme(darkTheme = darkTheme) {
                Surface {
                    MyBookstoreApp()
                }
            }
        }
    }
}
