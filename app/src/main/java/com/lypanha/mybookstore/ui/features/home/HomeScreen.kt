package com.lypanha.mybookstore.ui.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lypanha.mybookstore.R
import com.lypanha.mybookstore.Utils
import com.lypanha.mybookstore.model.Book
import com.lypanha.mybookstore.ui.components.BookCard
import com.lypanha.mybookstore.ui.components.BookstoreTopAppBar
import com.lypanha.mybookstore.ui.theme.MyBookstoreTheme

@Composable
fun HomeScreen(
    onDrawerMenuClick: () -> Unit,
    navigateToBookDetailsScreen: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    // test data
    val popBooks = remember {
        val popIds = setOf(21, 19, 1, 13)
        Utils.allBooks.filter { book -> popIds.contains(book.id) }
    }

    val newBooks = remember {
        mutableListOf<Book>().apply {
            Utils.allBooks.forEach { book ->
                if (book.id == 20 || book.id == 24 || book.id == 18) {
                    add(book)
                }
            }
        }.toList()
    }

    HomeContent(
        modifier = modifier,
        popularBooks = popBooks,
        newBooks = newBooks,
        onBookClick = { book -> navigateToBookDetailsScreen(book.id) },
        onDrawerMenuClick = onDrawerMenuClick
    )
}

@Composable
private fun HomeContent(
    popularBooks: List<Book>,
    newBooks: List<Book>,
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(R.drawable.ic_logo_large),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = stringResource(R.string.popular),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 20.dp, top = 20.dp)
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(horizontal = 20.dp)
            ) {
                items(
                    items = popularBooks,
                    key = { book -> book.id }
                ) { book ->
                    BookCard(
                        book = book,
                        onClick = { onBookClick(book) }
                    )
                }
            }
            Text(
                text = stringResource(R.string.new_releases),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 20.dp, top = 20.dp)
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(horizontal = 20.dp)
            ) {
                items(
                    items = newBooks,
                    key = { book -> book.id }
                ) { book ->
                    BookCard(
                        book = book,
                        onClick = { onBookClick(book) }
                    )
                }
            }
        }
    }

}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun HomeContentPreview() {
    val popBooks = Utils.allBooks.filter { book -> book.id in setOf(21, 19, 1, 13) }
    val newBooks = Utils.allBooks.filter { book -> book.id in setOf(20, 24, 18) }

    MyBookstoreTheme {
        HomeContent(
            popBooks,
            newBooks,
            {},
            {}
        )
    }
}
