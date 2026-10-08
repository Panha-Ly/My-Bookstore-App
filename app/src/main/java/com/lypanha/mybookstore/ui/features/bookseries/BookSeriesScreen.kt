package com.lypanha.mybookstore.ui.features.bookseries

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lypanha.mybookstore.BookActivity
import com.lypanha.mybookstore.Utils
import com.lypanha.mybookstore.model.Book
import com.lypanha.mybookstore.model.BookSeries
import com.lypanha.mybookstore.ui.components.BookSeriesCard
import com.lypanha.mybookstore.ui.components.DetailsBookCard
import com.lypanha.mybookstore.ui.theme.MyBookstoreTheme

@Composable
fun BookSeriesScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Book by series map
    val booksBySeries = remember {
        Utils.allBookSeriesNames.associateWith { series ->
            Utils.allBooks.filter { book -> book.seriesId == series.seriesId }
        }
    }

    BookSeriesContent(
        booksBySeries = booksBySeries,
        modifier = modifier,
        onBookClick = { book ->
            val intent = Intent(context, BookActivity::class.java).apply {
                putExtra("bookId", book.id)
                putExtra("fragmentName", "Book Series")
            }
            context.startActivity(intent)
        }
    )
}

@Composable
private fun BookSeriesContent(
    booksBySeries: Map<BookSeries, List<Book>>,
    onBookClick: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(vertical = 10.dp)
    ) {
        booksBySeries.forEach { (series, books) ->
            stickyHeader(key = series.seriesId) {
                BookSeriesCard(series)
            }
            items(
                items = books,
                key = { book -> book.id }
            ){ book ->
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
private fun BookSeriesContentPreview() {
    val series = Utils.allBookSeriesNames.associateWith { series ->
        Utils.allBooks.filter { book -> book.seriesId == series.seriesId }
    }

    MyBookstoreTheme {
        Surface(
            color = Color.Red
        ) {
            BookSeriesContent(
                series,
                {}
            )
        }
    }
}