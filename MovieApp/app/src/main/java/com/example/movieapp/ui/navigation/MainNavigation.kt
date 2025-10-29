// En: ui/navigation/MainNavigation.kt
package com.example.movieapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieapp.ui.screens.DetailScreen
import com.example.movieapp.ui.screens.FavoritesScreen
import com.example.movieapp.ui.screens.HomeScreen
import com.example.movieapp.viewmodel.MainViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigation(factory: MainViewModelFactory) {
    val navController = rememberNavController()

    Scaffold(
        topBar = { TopAppBar(title = { Text("🎬 MovieApp") }) }
    ) { innerPadding ->

        // <-- Aplicamos innerPadding (PaddingValues) con Modifier.padding(innerPadding)
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                HomeScreen(
                    factory = factory,
                    onMovieClick = { _: Int -> /* navegación desactivada temporalmente */ }
                )
            }

            composable("favorites") {
                FavoritesScreen(
                    factory = factory,
                    onBack = { navController.popBackStack() },
                    onMovieClick = { _: Int -> /* navegación desactivada temporalmente */ }
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
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
