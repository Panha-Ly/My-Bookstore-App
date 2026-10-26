package com.lypanha.mybookstore.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lypanha.mybookstore.ui.features.about.AboutScreen
import com.lypanha.mybookstore.ui.features.allbooks.AllBooksScreen
import com.lypanha.mybookstore.ui.features.bookseries.BookSeriesScreen
import com.lypanha.mybookstore.ui.features.cart.CartScreen
import com.lypanha.mybookstore.ui.features.home.HomeScreen
import com.lypanha.mybookstore.ui.features.settings.SettingsScreen

@Composable
fun MainNavGraph(
    mainNavController: NavHostController,
    navigateToBookDetailsScreen: (Int) -> Unit,
    onDrawerMenuClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = mainNavController,
        startDestination = AppRoute.MainSubRoute.Home,
        modifier = modifier.fillMaxSize()
    ) {
        composable<AppRoute.MainSubRoute.Home> {
            HomeScreen(
                onDrawerMenuClick = onDrawerMenuClick,
                navigateToBookDetailsScreen = navigateToBookDetailsScreen
            )
        }

        composable<AppRoute.MainSubRoute.Cart> {
            CartScreen(
                onDrawerMenuClick = onDrawerMenuClick,
                navigateToBookDetailsScreen = navigateToBookDetailsScreen
            )
        }

        composable<AppRoute.MainSubRoute.BookSeries> {
            BookSeriesScreen(
                onDrawerMenuClick = onDrawerMenuClick,
                navigateToBookDetailsScreen = navigateToBookDetailsScreen
            )
        }

        composable<AppRoute.MainSubRoute.AllBooks> {
            AllBooksScreen(
                onDrawerMenuClick = onDrawerMenuClick,
                navigateToBookDetailsScreen = navigateToBookDetailsScreen
            )
        }

        composable<AppRoute.MainSubRoute.About> {
            AboutScreen(onDrawerMenuClick = onDrawerMenuClick)
        }

        composable<AppRoute.MainSubRoute.Settings> {
            SettingsScreen(onDrawerMenuClick = onDrawerMenuClick)
        }
    }
}