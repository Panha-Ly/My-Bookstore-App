package com.lypanha.mybookstore.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.lypanha.mybookstore.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookstoreTopAppBar(
    title: String,
    navigationIcon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors()
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = navigationIcon,
        modifier = modifier,
        colors = colors
    )
}

@Preview
@Composable
private fun BookstoreTopAppBarPreview() {
    BookstoreTopAppBar(
        title = "",
        navigationIcon = {
            IconButton({}) {
                Icon(
                    painter = painterResource(R.drawable.baseline_menu_24),
                    contentDescription = null
                )
            }
        }
    )
}