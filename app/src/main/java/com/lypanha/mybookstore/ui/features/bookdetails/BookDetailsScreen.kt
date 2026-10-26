package com.lypanha.mybookstore.ui.features.bookdetails

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.lypanha.mybookstore.R
import com.lypanha.mybookstore.Utils
import com.lypanha.mybookstore.model.Book
import com.lypanha.mybookstore.ui.components.BookstoreTopAppBar
import com.lypanha.mybookstore.ui.theme.MyBookstoreTheme
import com.lypanha.mybookstore.ui.theme.accentColor
import kotlinx.coroutines.launch

@Composable
fun BookDetailsScreen(
    bookId: Int,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Lock Status bar colors to dark theme (white icons) + 3 buttons navigation bar to dark theme as well
    val window = LocalActivity.current?.window
    val isDarkTheme = when (Utils.appThemeMode) {
        Utils.THEME_DARK -> true
        Utils.THEME_LIGHT -> false
        else -> isSystemInDarkTheme() // If "system", follow OS directly
    }
    // 1. Force the white icons on EVERY recomposition pass.
    // This runs after the main AppTheme finishes its updates, locking icons to white.
    SideEffect {
        if (!isDarkTheme) {
            window?.let {
                WindowCompat.getInsetsController(it, it.decorView).apply {
                    isAppearanceLightStatusBars = false // false = White icons
                    isAppearanceLightNavigationBars = false
                }
            }
        }
    }
    // 2. Keep the DisposableEffect strictly for restoring the status bar when the user leaves.
    DisposableEffect(Unit) {
        onDispose {
            if (!isDarkTheme) {
                window?.let {
                    // Read fresh app configuration flags right when navigating away
                    WindowCompat.getInsetsController(it, it.decorView).apply {
                        isAppearanceLightStatusBars = true
                        isAppearanceLightNavigationBars = true
                    }
                }
            }
        }
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val resources = LocalResources.current
    val currentBook = remember { requireNotNull(Utils.getBookById(bookId)) }
    val isInCart by remember {
        derivedStateOf { Utils.booksInCart.any { it.id == currentBook.id } }
    }

    BookDetailsContent(
        book = currentBook,
        snackbarHostState = snackbarHostState,
        onCartClick = {
            scope.launch {
                val success = if (isInCart) {
                    Utils.removeFromCart(currentBook.id)
                } else {
                    Utils.addToCart(currentBook.id)
                }
                val snackbarText = when {
                    success && isInCart -> R.string.added_to_cart
                    success && !isInCart -> R.string.removed_from_cart
                    !success && isInCart -> R.string.already_added_to_cart
                    else -> R.string.already_removed_from_cart
                }
                snackbarHostState.showSnackbar(
                    resources.getString(snackbarText)
                )
            }
        },
        onBackClick = navigateUp,
        cartButtonTitle = stringResource(
            if (isInCart) R.string.remove_from_cart else R.string.add_to_cart
        ),
        modifier = modifier
    )
}

@Composable
private fun BookDetailsContent(
    book: Book,
    snackbarHostState: SnackbarHostState,
    cartButtonTitle: String,
    onCartClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        containerColor = Color.Black,
        modifier = modifier.fillMaxSize(),
        topBar = {
            BookstoreTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    navigationIconContentColor = Color.White,
                    containerColor = Color.Transparent
                ),
                title = "",
                navigationIcon = {
                    IconButton(onBackClick) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_arrow_back_ios_new_24),
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState) { snackbarData ->
                val dismissState = rememberSwipeToDismissBoxState()
                SwipeToDismissBox(
                    state = dismissState,
                    backgroundContent = {},
                    onDismiss = { value ->
                        if (value == SwipeToDismissBoxValue.StartToEnd ||
                            value == SwipeToDismissBoxValue.EndToStart
                        ) {
                            snackbarData.dismiss()
                        }
                    }
                ) { Snackbar(snackbarData = snackbarData) }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
                .consumeWindowInsets(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // IMAGE + TEXTS ON TOP OF IMAGE
                Box(Modifier.fillMaxWidth()) {
                    // The main cover image
                    Image(
                        painter = painterResource(book.imageId),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.FillWidth
                    )
                    // The scrim gradient overlay (book_gradient)
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.5f),
                                        Color.Black.copy(alpha = 1f)
                                    )
                                )
                            )
                    )

                    // TEXTS ON TOP (e.g., Book Title) pinned to the bottom edge of the image
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .align(Alignment.BottomStart)
                    ) {
                        Text(
                            text = book.name,
                            color = Color.White,
                            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                        )
                        Text(
                            text = stringResource(R.string.author, book.author),
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        )
                    }
                }
                // TEXTS COMPLETELY BELOW THE IMAGE
                // Any composable placed here sits right underneath the image Box automatically
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(R.string.publication_date, book.publicationDate),
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = stringResource(R.string.price, book.price),
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = stringResource(R.string.about),
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = book.description,
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Box(Modifier.align(Alignment.TopEnd)) {
                        Button(
                            shape = RoundedCornerShape(5.dp),
                            onClick = onCartClick,
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White,
                                containerColor = accentColor
                            ),
                            modifier = Modifier.padding(top = 40.dp)
                        ) {
                            Text(text = cartButtonTitle)
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true, showSystemUi = true)
@Preview(name = "Dark Mode", showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun BookDetailsContentPreview() {
    val snackbarHostState = SnackbarHostState()

    LaunchedEffect(Unit) {
        snackbarHostState.showSnackbar("Test")
    }

    MyBookstoreTheme {
        BookDetailsContent(
            Utils.allBooks[0],
            snackbarHostState,
            stringResource(R.string.add_to_cart),
            {},
            {}
        )
    }
}
