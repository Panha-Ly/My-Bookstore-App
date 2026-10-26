package com.lypanha.mybookstore.ui.features.allbooks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lypanha.mybookstore.R
import com.lypanha.mybookstore.Utils
import com.lypanha.mybookstore.model.Book
import com.lypanha.mybookstore.ui.components.BookstoreTopAppBar
import com.lypanha.mybookstore.ui.components.DetailsBookCard
import com.lypanha.mybookstore.ui.theme.MyBookstoreTheme
import kotlin.random.Random

@Composable
fun AllBooksScreen(
    onDrawerMenuClick: () -> Unit,
    navigateToBookDetailsScreen: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    // All books list
    val allBooks = remember {
        Utils.allBooks
            .shuffled(Random(69)) // Shuffle the books in the list
    }

    AllBooksContent(
        books = allBooks,
        modifier = modifier,
        onBookClick = { book -> navigateToBookDetailsScreen(book.id) },
        onDrawerMenuClick = onDrawerMenuClick
    )
}

@Composable
private fun AllBooksContent(
    books: List<Book>,
    onBookClick: (Book) -> Unit,
    onDrawerMenuClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            BookstoreTopAppBar(
                "",
                navigationIcon = {
                    IconButton(onClick = onDrawerMenuClick) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_menu_24),
                            contentDescription = stringResource(R.string.menu_button)
                        )
                    }
                }
            )
        },
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(10.dp)
        ) {
            items(
                items = books,
                key = { book -> book.id }
            ) { book ->
                DetailsBookCard(
                    book = book,
                    onClick = { onBookClick(book) }
                )
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun AllBooksContentPreview() {
    MyBookstoreTheme {
        AllBooksContent(
            Utils.allBooks.shuffled(Random(69)),
            {},
            {}
        )
    }
}
