package com.example.movieapp.ui.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.movieapp.viewmodel.MainViewModelFactory
import com.example.movieapp.data.auth.AuthPrefsRepo
import com.example.movieapp.ui.screens.auth.LoginScreen
import com.example.movieapp.ui.screens.auth.WelcomeScreen
import com.example.movieapp.ui.screens.auth.SignupScreen

@Composable
fun MainNavigation(factory: MainViewModelFactory) {
    val rootNavController = rememberNavController()
    val context = LocalContext.current

    var startDestination: String? by remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        val hasCreds = AuthPrefsRepo.hasCredentials(context)
        startDestination = if (hasCreds) {
            Routes.APP_FLOW
        } else {
            Routes.AUTH_FLOW
        }
    }

    if (startDestination == null) {
        return
    }

    NavHost(
        navController = rootNavController,
        startDestination = startDestination!!
    ) {
        navigation(
            startDestination = Routes.WELCOME,
            route = Routes.AUTH_FLOW
        ) {
            composable(Routes.WELCOME) {
                WelcomeScreen(navController = rootNavController)
            }

            composable(Routes.LOGIN) {
                LoginScreen(navController = rootNavController)
            }

            composable(Routes.SIGNUP) {
                SignupScreen(navController = rootNavController)
            }
        }

        composable(Routes.APP_FLOW) { //
            MovieAppFlow(
                factory = factory,
                rootNavController = rootNavController
            )
        }
    }
}