package com.example.movieapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieapp.ui.screens.DetailScreen
import com.example.movieapp.ui.screens.FavoritesScreen
import com.example.movieapp.ui.screens.HomeScreen
import com.example.movieapp.viewmodel.MainViewModelFactory


@Composable
fun MovieAppFlow(
    factory: MainViewModelFactory,
    rootNavController: NavHostController
) {
    val appNavController = rememberNavController()

    val navBackStackEntry by appNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    Scaffold(

        bottomBar = {

            if (currentRoute == BottomNavItem.Home.route || currentRoute == BottomNavItem.Favorites.route) {

                BottomNavigationBar(navController = appNavController)
            }
        }
    ) { innerPadding ->


        NavHost(
            navController = appNavController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable(BottomNavItem.Home.route) {
                HomeScreen(
                    factory = factory,
                    onMovieClick = { movieId -> appNavController.navigate("detail/$movieId") },

                    onLogout = {

                        rootNavController.navigate(Routes.AUTH_FLOW) {
                            popUpTo(rootNavController.graph.startDestinationId) { inclusive = true }
                        }
                    }
                )
            }

            composable(BottomNavItem.Favorites.route) {
                FavoritesScreen(
                    factory = factory,
                    onBack = { appNavController.popBackStack() },
                    onMovieClick = { movieId -> appNavController.navigate("detail/$movieId") }
                )
            }

            composable(
                route = "detail/{movieId}",
                arguments = listOf(navArgument("movieId") { type = NavType.IntType })
            ) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
                DetailScreen(
                    factory = factory,
                    movieId = movieId,
                    onBack = { appNavController.popBackStack() }
                )
            }
        }
    }
}