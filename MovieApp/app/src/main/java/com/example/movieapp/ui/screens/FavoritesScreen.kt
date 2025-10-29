package com.example.movieapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieapp.model.Movie
import com.example.movieapp.ui.common.MovieList
import com.example.movieapp.viewmodel.FavoritesViewModel
import com.example.movieapp.viewmodel.MainViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    factory: MainViewModelFactory,
    onBack: () -> Unit,
    onMovieClick: (Int) -> Unit
) {
    val viewModel: FavoritesViewModel = viewModel(factory = factory)
    val favoriteMovies by viewModel.favoriteMovies.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favoritos ❤️") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            if (favoriteMovies.isEmpty()) {
                Text("No tienes películas favoritas aún 😢")
            } else {
                // 🔹 Convertimos FavoriteMovie → Movie
                val movieList = favoriteMovies.map {
                    Movie(
                        id = it.id,
                        title = it.title,
                        overview = it.overview,
                        posterPath = it.posterPath,
                        releaseDate = null,
                        voteAverage = null
                    )
                }

                MovieList(
                    title = "Tus Favoritos",
                    movies = movieList,
                    onMovieClick = onMovieClick
                )
            }
        }
    }
}
