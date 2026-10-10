package com.lypanha.mybookstore.ui.features.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lypanha.mybookstore.Utils
import com.lypanha.mybookstore.ui.theme.MyBookstoreTheme
import com.lypanha.mybookstore.ui.theme.accentColor

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
    SettingsContent(
        selectedTheme = Utils.appThemeMode,
        onThemeChange = { selectedTheme ->
            // 1. Update global compose tracking state
            Utils.appThemeMode = selectedTheme

            // 2. Set the appropriate AppCompat state for XML layout styling
            val mode = when (selectedTheme) {
                Utils.THEME_DARK -> AppCompatDelegate.MODE_NIGHT_YES
                Utils.THEME_LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
                else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM // Tells XML to look at OS again!
            }
            AppCompatDelegate.setDefaultNightMode(mode)
        },
        modifier = modifier
    )
}

@Composable
private fun SettingsContent(
    selectedTheme: String,
    onThemeChange: (selectedTheme : String) -> Unit,
    modifier: Modifier = Modifier
) {
    val themeOptions = setOf(Utils.THEME_SYSTEM, Utils.THEME_DARK, Utils.THEME_LIGHT)
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 1. Section Title
        Text(
            text = "Theme",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // 2. The Radio Group (Vertical is best for readability and touch targets)
        Column(modifier = Modifier.selectableGroup()) {
            themeOptions.forEach { themeOption ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = selectedTheme == themeOption,
                            onClick = { onThemeChange(themeOption) },
                            role = Role.RadioButton // Crucial for accessibility!
                        )
                        // Fat padding makes the whole row easy to tap
                        .padding(vertical = 12.dp, horizontal = 4.dp)
                ) {
                    RadioButton(
                        selected = selectedTheme == themeOption,
                        onClick = null, // Correctly delegated to parent Row
                        colors = RadioButtonDefaults.colors(selectedColor = accentColor)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = themeOption.replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun SettingsContentPreview() {
    val darkTheme = when (Utils.appThemeMode) {
        Utils.THEME_DARK -> true
        Utils.THEME_LIGHT -> false
        else -> isSystemInDarkTheme() // If "system", follow OS directly
    }
    MyBookstoreTheme(darkTheme = darkTheme) {
        Surface {
            SettingsContent(
                selectedTheme = Utils.appThemeMode,
                onThemeChange = { selectedTheme ->
                    // 1. Update global compose tracking state
                    Utils.appThemeMode = selectedTheme

                    // 2. Set the appropriate AppCompat state for XML layout styling
                    val mode = when (selectedTheme) {
                        Utils.THEME_DARK -> AppCompatDelegate.MODE_NIGHT_YES
                        Utils.THEME_LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
                        else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM // Tells XML to look at OS again!
                    }
                    AppCompatDelegate.setDefaultNightMode(mode)
                }
            )
        }
    }
}
