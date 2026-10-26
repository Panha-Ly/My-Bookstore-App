package com.lypanha.mybookstore.ui.features.main

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lypanha.mybookstore.R
import com.lypanha.mybookstore.Utils
import com.lypanha.mybookstore.model.User
import com.lypanha.mybookstore.ui.navigation.AppRoute
import com.lypanha.mybookstore.ui.navigation.MainNavGraph
import com.lypanha.mybookstore.ui.theme.MyBookstoreTheme
import com.lypanha.mybookstore.ui.theme.accentColor
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    navigateToLoginScreen: () -> Unit,
    navigateToBookDetailsScreen: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val mainNavController = rememberNavController()
    val currentBackStackEntry by mainNavController.currentBackStackEntryAsState()
    val currentDrawerRoute: AppRoute.MainSubRoute? = remember(currentBackStackEntry) {
        val destination = currentBackStackEntry?.destination
        // Find the drawer route type that matches the current destination
        val currentDrawerType = MainDrawerItemType.entries.find { drawerItemType ->
            destination?.hasRoute(drawerItemType.route::class) == true
        }
        currentDrawerType?.route
    }
    val loggedInUser by remember {
        derivedStateOf { Utils.users.find { it.isLoginStatus } }
    }

    MainContent(
        currentUser = loggedInUser,
        currentRoute = currentDrawerRoute,
        drawerState = drawerState,
        navigateToLoginScreen = navigateToLoginScreen,
        onDrawerItemClick = { currentRoute ->
            scope.launch {
                mainNavController.navigate(currentRoute) {
                    popUpTo(AppRoute.MainSubRoute.Home) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
                drawerState.close()
            }
        },
        modifier = modifier
    ) {
        MainNavGraph(
            onDrawerMenuClick = { scope.launch { drawerState.open() } },
            mainNavController = mainNavController,
            navigateToBookDetailsScreen = navigateToBookDetailsScreen
        )
    }
}

@Composable
private fun MainContent(
    currentUser: User?,
    currentRoute: AppRoute.MainSubRoute?,
    drawerState: DrawerState,
    navigateToLoginScreen: () -> Unit,
    onDrawerItemClick: (AppRoute.MainSubRoute) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit // Use Slot Pattern for the NavGraph
) {
    // Hoist this up if necessary
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val resources = LocalResources.current

    Scaffold(
        contentWindowInsets = WindowInsets(),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = modifier.navigationBarsPadding() // Apply Nav bar padding (needed cuz windowInsets is now 0)
            ) { snackbarData ->
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
        ModalNavigationDrawer(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(drawerState) {
                    MainDrawerHeader(
                        user = currentUser,
                        onLoginClick = navigateToLoginScreen,
                        onLogoutClick = {
                            scope.launch {
                                snackbarHostState.showSnackbar(resources.getString(R.string.logged_out))
                            }
                        }
                    )
                    LazyColumn(Modifier.fillMaxHeight()) {
                        items(
                            items = MainDrawerItemType.entries,
                            key = { it.name }
                        ) { drawerItemType ->
                            MainDrawerItem(
                                label = drawerItemType.label,
                                selected = currentRoute != null && currentRoute::class == drawerItemType.route::class,
                                onClick = { onDrawerItemClick(drawerItemType.route) }
                            )
                        }
                    }
                }
            },
            content = content
        )
    }
}

/**
 * Show a Login Button when [user] is null, otherwise show the user info
 * along with a Logout Button
 * @param user Current logged-in user.
 */
@Composable
private fun MainDrawerHeader(
    user: User?,
    onLoginClick: () -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp, 50.dp, 20.dp, 20.dp)
    ) {
        if (user != null) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(user.imageId),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )
                    Button(
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = accentColor,
                            contentColor = Color.White
                        ),
                        onClick = {
                            Utils.setLoginStatus(user.id, false) // Show Log in Button
                            onLogoutClick()
                        }
                    ) { Text(stringResource(R.string.logout)) }
                }
                Text(stringResource(R.string.welcome_user, user.username))
                Text(user.email)
            }
        } else {
            Button(
                modifier = modifier
                    .width(200.dp)
                    .align(Alignment.Center),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = accentColor,
                    contentColor = Color.White
                ),
                onClick = onLoginClick // Go to Log in screen
            ) { Text(stringResource(R.string.log_in)) }
        }
    }
}

@Composable
private fun MainDrawerItem(
    @StringRes label: Int,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationDrawerItem(
        label = { Text(stringResource(label)) },
        selected = selected,
        onClick = onClick,
        shape = RectangleShape,
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = accentColor,
            selectedTextColor = Color.White
        ),
        modifier = modifier
    )
}

enum class MainDrawerItemType(
    val route: AppRoute.MainSubRoute,
    @StringRes val label: Int,
) {
    HOME(AppRoute.MainSubRoute.Home, R.string.home),
    CART(AppRoute.MainSubRoute.Cart, R.string.your_cart),
    BROWSE_BY_SERIES(AppRoute.MainSubRoute.BookSeries, R.string.browse_by_series),
    ALL_BOOKS(AppRoute.MainSubRoute.AllBooks, R.string.all_books),
    ABOUT(AppRoute.MainSubRoute.About, R.string.about_us),
    SETTINGS(AppRoute.MainSubRoute.Settings, R.string.settings)
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun MainContentPreview() {
    MyBookstoreTheme {
        MainContent(
            currentUser = Utils.users[1],
            currentRoute = AppRoute.MainSubRoute.BookSeries,
            onDrawerItemClick = {},
            drawerState = rememberDrawerState(DrawerValue.Open),
            navigateToLoginScreen = {},
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) { Text("Fake Screen") }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
private fun MainDrawerHeaderPreview() {
    MyBookstoreTheme {
        MainDrawerHeader(
            user = Utils.users[0],
            onLoginClick = {},
            onLogoutClick = {}
        )
    }
}
