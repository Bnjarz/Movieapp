package com.example.movieapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieapp.ui.screens.DetailScreen
import com.example.movieapp.ui.screens.FavoritesScreen
import com.example.movieapp.ui.screens.HomeScreen
import com.example.movieapp.ui.screens.ProfileScreen
import com.example.movieapp.ui.screens.auth.LoginScreen
import com.example.movieapp.ui.screens.auth.SignupScreen
import com.example.movieapp.ui.screens.auth.WelcomeScreen
import com.example.movieapp.viewmodel.MainViewModelFactory

@Composable
fun MainNavigation(factory: MainViewModelFactory) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf("home", "favorites", "profile")

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "welcome",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("welcome") {
                WelcomeScreen(navController)
            }

            composable("login") {
                LoginScreen(navController, factory)
            }
            composable("signup") {
                SignupScreen(navController, factory)
            }

            composable("home") {
                HomeScreen(
                    factory = factory,
                    onMovieClick = { movieId ->
                        navController.navigate("detail/$movieId")
                    },
                    onLogout = {
                        navController.navigate("welcome") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
                )
            }

            composable("favorites") {
                FavoritesScreen(
                    factory = factory,
                    onBack = { navController.popBackStack() },
                    onMovieClick = { movieId ->
                        navController.navigate("detail/$movieId")
                    }
                )
            }

            composable("profile") {
                ProfileScreen(factory = factory)
            }

            composable(
                route = "detail/{movieId}",
                arguments = listOf(navArgument("movieId") { type = NavType.IntType })
            ) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
                DetailScreen(
                    movieId = movieId,
                    factory = factory,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}