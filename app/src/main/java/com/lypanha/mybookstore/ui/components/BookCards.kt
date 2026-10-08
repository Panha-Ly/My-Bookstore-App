package com.lypanha.mybookstore.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lypanha.mybookstore.Utils
import com.lypanha.mybookstore.model.Book
import com.lypanha.mybookstore.model.BookSeries
import com.lypanha.mybookstore.ui.theme.MyBookstoreTheme

@Composable
fun BookCard(
    book: Book,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.width(IntrinsicSize.Min),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Image(
            painter = painterResource(book.imageId),
            contentDescription = book.name,
            modifier = Modifier.height(240.dp)
        )
        Text(
            text = book.name,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(5.dp)
        )
    }

}

@Composable
fun DetailsBookCard(
    book: Book,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            Image(
                painter = painterResource(book.imageId),
                contentDescription = book.name,
                modifier = Modifier.width(150.dp)
            )
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .matchParentSize() // Binds the column height EXCLUSIVELY to the measured row height
                        .padding(10.dp)
                ) {
                    Text(
                        text = book.name,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = book.description,
                        style = MaterialTheme.typography.bodyMedium,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }

}

@Composable
fun BookSeriesCard(
    bookSeries: BookSeries,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RectangleShape,
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Text(
            text = bookSeries.name,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BookCardPreview() {
    MyBookstoreTheme {
        Surface(
            color = Color.Red
        ) {
            BookCard(
                book = Utils.allBooks[6],
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailsBookCardPreview() {
    MyBookstoreTheme {
        Surface(
            color = Color.Red
        ) {
            DetailsBookCard(
                book = Utils.allBooks[0],
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BookSeriesCardPreview() {
    MyBookstoreTheme {
        Surface(
            color = Color.Red
        ) {
            BookSeriesCard(
                bookSeries = Utils.allBookSeriesNames[1]
            )
        }
    }
}
