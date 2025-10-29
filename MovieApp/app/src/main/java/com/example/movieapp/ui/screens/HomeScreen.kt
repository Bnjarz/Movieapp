package com.example.movieapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieapp.ui.common.MovieList
import com.example.movieapp.viewmodel.HomeViewModel
import com.example.movieapp.viewmodel.MainViewModelFactory

@Composable
fun HomeScreen(
    factory: MainViewModelFactory,
    onMovieClick: (Int) -> Unit
) {
    val viewModel: HomeViewModel = viewModel(factory = factory)

    val trendingMovies by viewModel.trendingMovies.collectAsState()
    val nowPlayingMovies by viewModel.nowPlayingMovies.collectAsState()
    val horrorMovies by viewModel.horrorMovies.collectAsState()
    val romanceMovies by viewModel.romanceMovies.collectAsState()

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        MovieList("En Tendencia", trendingMovies, onMovieClick)
        MovieList("Recientes", nowPlayingMovies, onMovieClick)
        MovieList("Terror", horrorMovies, onMovieClick)
        MovieList("Romance", romanceMovies, onMovieClick)
        Spacer(modifier = Modifier.height(16.dp))
    }
}
