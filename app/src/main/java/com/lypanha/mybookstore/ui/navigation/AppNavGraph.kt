package com.lypanha.mybookstore.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.lypanha.mybookstore.ui.features.bookdetails.BookDetailsScreen
import com.lypanha.mybookstore.ui.features.login.LoginScreen
import com.lypanha.mybookstore.ui.features.main.MainScreen
import kotlinx.serialization.Serializable

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = AppRoute.Main,
        modifier = modifier.fillMaxSize()
    ) {
        composable<AppRoute.Main> {
            MainScreen(
                navigateToLoginScreen = { navController.navigate(AppRoute.Login) },
                navigateToBookDetailsScreen = { bookId ->
                    navController.navigate(AppRoute.BookDetails(bookId))
                }
            )
        }

        composable<AppRoute.Login> {
            LoginScreen(navigateUp = { navController.navigateUp() })
        }

        composable<AppRoute.BookDetails> { currentBackStack ->
            val bookId = currentBackStack.toRoute<AppRoute.BookDetails>().bookId
            BookDetailsScreen(
                bookId = bookId,
                navigateUp = { navController.navigateUp() }
            )
        }
    }
}

sealed interface AppRoute {
    // Root level direct implementations
    @Serializable
    data object Login : AppRoute

    @Serializable
    data object Main : AppRoute

    @Serializable
    data class BookDetails(val bookId: Int) : AppRoute

    // A subgroup interface that inherits from AppRoute
    sealed interface MainSubRoute : AppRoute {
        @Serializable
        data object Home : MainSubRoute

        @Serializable
        data object Cart : MainSubRoute

        @Serializable
        data object AllBooks : MainSubRoute

        @Serializable
        data object BookSeries : MainSubRoute

        @Serializable
        data object About : MainSubRoute

        @Serializable
        data object Settings : MainSubRoute
    }
}
