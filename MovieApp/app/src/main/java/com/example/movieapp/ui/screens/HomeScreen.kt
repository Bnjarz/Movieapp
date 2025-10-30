package com.example.movieapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
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
    onMovieClick: (Int) -> Unit,
    onLogout: () -> Unit
) {
    val viewModel: HomeViewModel = viewModel(factory = factory)

    val trendingMovies by viewModel.trendingMovies.collectAsState()
    val nowPlayingMovies by viewModel.nowPlayingMovies.collectAsState()
    val horrorMovies by viewModel.horrorMovies.collectAsState()
    val romanceMovies by viewModel.romanceMovies.collectAsState()

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(top = 16.dp)
    ) {

        Button(
            onClick = onLogout,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text("Cerrar Sesión")
        }

        Spacer(modifier = Modifier.height(8.dp))

        MovieList(
            title = "Tendencia de la Semana",
            movies = trendingMovies,
            onMovieClick = onMovieClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        MovieList(
            title = "Recién Estrenadas",
            movies = nowPlayingMovies,
            onMovieClick = onMovieClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        MovieList(
            title = "Terror",
            movies = horrorMovies,
            onMovieClick = onMovieClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        MovieList(
            title = "Romance",
            movies = romanceMovies,
            onMovieClick = onMovieClick
        )



        Spacer(modifier = Modifier.height(64.dp))
    }
}