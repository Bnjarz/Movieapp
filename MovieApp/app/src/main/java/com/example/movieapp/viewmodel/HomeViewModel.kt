package com.example.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.Movie
import com.example.movieapp.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _trendingMovies = MutableStateFlow<List<Movie>>(emptyList())
    val trendingMovies: StateFlow<List<Movie>> = _trendingMovies

    private val _nowPlayingMovies = MutableStateFlow<List<Movie>>(emptyList())
    val nowPlayingMovies: StateFlow<List<Movie>> = _nowPlayingMovies

    private val _horrorMovies = MutableStateFlow<List<Movie>>(emptyList())
    val horrorMovies: StateFlow<List<Movie>> = _horrorMovies

    private val _romanceMovies = MutableStateFlow<List<Movie>>(emptyList())
    val romanceMovies: StateFlow<List<Movie>> = _romanceMovies

    init {
        viewModelScope.launch {
            _trendingMovies.value = repository.getTrendingMovies()
            _nowPlayingMovies.value = repository.getNowPlayingMovies()
            _horrorMovies.value = repository.getHorrorMovies()
            _romanceMovies.value = repository.getRomanceMovies()
        }
    }
}