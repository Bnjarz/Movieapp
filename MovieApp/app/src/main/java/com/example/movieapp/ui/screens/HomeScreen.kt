package com.example.movieapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieapp.ui.common.MovieList
import com.example.movieapp.viewmodel.HomeViewModel
import com.example.movieapp.viewmodel.MainViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    factory: MainViewModelFactory,
    onMovieClick: (Int) -> Unit,
    onLogout: () -> Unit
) {
    val viewModel: HomeViewModel = viewModel(factory = factory)

    val trendingMovies by viewModel.trendingMovies.collectAsState()
    val nowPlayingMovies by viewModel.nowPlayingMovies.collectAsState()
    val horrorMovies by viewModel.horrorMovies.collectAsState()
    val romanceMovies by viewModel.romanceMovies.collectAsState()

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MovieApp") },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Cerrar Sesión"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {

            if (trendingMovies.isNotEmpty()) {
                MovieList(title = "Tendencias", movies = trendingMovies, onMovieClick = onMovieClick)
            }

            if (nowPlayingMovies.isNotEmpty()) {
                MovieList(title = "Populares", movies = nowPlayingMovies, onMovieClick = onMovieClick)
            }

            if (horrorMovies.isNotEmpty()) {
                MovieList(title = "Terror", movies = horrorMovies, onMovieClick = onMovieClick)
            }

            if (romanceMovies.isNotEmpty()) {
                MovieList(title = "Romance", movies = romanceMovies, onMovieClick = onMovieClick)
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}