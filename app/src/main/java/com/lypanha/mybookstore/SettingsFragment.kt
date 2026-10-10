package com.lypanha.mybookstore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.lypanha.mybookstore.Utils.appThemeMode
import com.lypanha.mybookstore.ui.features.settings.SettingsScreen
import com.lypanha.mybookstore.ui.theme.MyBookstoreTheme

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                // Determine the actual UI rendering color mode for the local Compose wrapper
                val darkTheme = when (appThemeMode) {
                    Utils.THEME_DARK -> true
                    Utils.THEME_LIGHT -> false
                    else -> isSystemInDarkTheme() // If "system", follow OS directly
                }
                MyBookstoreTheme(darkTheme = darkTheme) {
                    Surface {
                        SettingsScreen()
                    }
                }
            }
        }
    }

}