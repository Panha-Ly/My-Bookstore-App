package com.lypanha.mybookstore.ui.features.cart

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lypanha.mybookstore.BookActivity
import com.lypanha.mybookstore.R
import com.lypanha.mybookstore.Utils
import com.lypanha.mybookstore.model.Book
import com.lypanha.mybookstore.ui.components.DetailsBookCard
import com.lypanha.mybookstore.ui.theme.MyBookstoreTheme
import kotlin.math.abs

@Composable
fun CartScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Cart books list
    val cartBooks = Utils.booksInCart

    CartContent(
        books = cartBooks,
        modifier = modifier,
        onBookClick = { book ->
            val intent = Intent(context, BookActivity::class.java).apply {
                putExtra("bookId", book.id)
                putExtra("fragmentName", "Cart")
            }
            context.startActivity(intent)
        }
    )
}

@Composable
private fun CartContent(
    books: List<Book>,
    onBookClick: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    if(books.isEmpty()){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ){
            Text(
                text = stringResource(R.string.no_books_in_cart),
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )
        }
    } else {
        // fix lazy list automatic scrolling up when swiping to a positional threshold
        //  while last list item is visible on the screen
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(vertical = 10.dp)
        ) {
            items(
                items = books,
                key = { book -> book.id }
            ){ book ->
                val dismissState = rememberSwipeToDismissBoxState()
                SwipeToDismissBox(
                    state = dismissState,
                    enableDismissFromStartToEnd = false,
                    backgroundContent = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            // Background color Box
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .layout { measurable, constraints ->
                                        // Fully safe to read directly now without try/catch!
                                        val swipeOffsetPx = try {
                                            abs(dismissState.requireOffset()).toInt()
                                        } catch (_: IllegalStateException) {
                                            0
                                        }
                                        // Force the width to be exactly the amount swiped
                                        val placeable = measurable.measure(
                                            constraints.copy(minWidth = swipeOffsetPx, maxWidth = swipeOffsetPx)
                                        )
                                        layout(placeable.width, placeable.height) {
                                            placeable.placeRelative(0, 0)
                                        }
                                    }
                                    .padding(horizontal = 5.dp)
                                    .background(MaterialTheme.colorScheme.error, RoundedCornerShape(50))
                            ) {}
                            // Icon Box
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .layout { measurable, constraints ->
                                        // Fully safe to read directly now without try/catch!
                                        val swipeOffsetPx = try {
                                            abs(dismissState.requireOffset()).toInt()
                                        } catch (_: IllegalStateException) {
                                            0
                                        }
                                        val defaultWidthPx = 64.dp.toPx().toInt()
                                        val targetWidth = if (swipeOffsetPx > defaultWidthPx) swipeOffsetPx else defaultWidthPx
                                        // Force the width to be exactly the amount swiped or the default width of 64dp if the offset is smaller
                                        val placeable = measurable.measure(
                                            constraints.copy(minWidth = targetWidth, maxWidth = targetWidth)
                                        )
                                        layout(placeable.width, placeable.height) {
                                            placeable.placeRelative(0, 0)
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.delete_24px),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onError,
                                )
                            }
                        }
                    },
                    onDismiss = { direction ->
                        if (direction == SwipeToDismissBoxValue.EndToStart) {
                            Utils.removeFromCart(book.id)
                        }
                    },
                    modifier = Modifier.animateItem()
                ) {
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
private fun CartContentPreview() {
    MyBookstoreTheme {
        Surface(
            color = Color.Red.copy(alpha = 0.2f)
        ) {
            CartContent(
                listOf(Utils.allBooks[9]),
                {}
            )
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun EmptyCartContentPreview() {
    MyBookstoreTheme {
        Surface {
            CartContent(
                Utils.booksInCart,
                {}
            )
        }
    }
}