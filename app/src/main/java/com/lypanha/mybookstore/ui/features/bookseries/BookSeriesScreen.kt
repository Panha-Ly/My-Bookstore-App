package com.lypanha.mybookstore.ui.features.bookseries

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
import com.lypanha.mybookstore.model.BookSeries
import com.lypanha.mybookstore.ui.components.BookSeriesCard
import com.lypanha.mybookstore.ui.components.BookstoreTopAppBar
import com.lypanha.mybookstore.ui.components.DetailsBookCard
import com.lypanha.mybookstore.ui.theme.MyBookstoreTheme

@Composable
fun BookSeriesScreen(
    onDrawerMenuClick: () -> Unit,
    navigateToBookDetailsScreen: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    // Book by series map
    val booksBySeries = remember {
        Utils.allBookSeriesNames.associateWith { series ->
            Utils.allBooks.filter { book -> book.seriesId == series.seriesId }
        }
    }

    BookSeriesContent(
        booksBySeries = booksBySeries,
        modifier = modifier,
        onBookClick = { book -> navigateToBookDetailsScreen(book.id) },
        onDrawerMenuClick = onDrawerMenuClick
    )
}

@Composable
private fun BookSeriesContent(
    booksBySeries: Map<BookSeries, List<Book>>,
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
            booksBySeries.forEach { (series, books) ->
                stickyHeader(key = series.seriesId) {
                    BookSeriesCard(series)
                }
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

}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun BookSeriesContentPreview() {
    val series = Utils.allBookSeriesNames.associateWith { series ->
        Utils.allBooks.filter { book -> book.seriesId == series.seriesId }
    }

    MyBookstoreTheme {
        BookSeriesContent(
            series,
            {},
            {}
        )
    }
}
