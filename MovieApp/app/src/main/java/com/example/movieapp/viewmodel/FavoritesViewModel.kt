package com.example.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.database.FavoriteMovie
import com.example.movieapp.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _favoriteMovies = MutableStateFlow<List<FavoriteMovie>>(emptyList())
    val favoriteMovies: StateFlow<List<FavoriteMovie>> = _favoriteMovies.asStateFlow()

    init {
        getFavorites()
    }

    fun getFavorites() {
        viewModelScope.launch {
            repository.getAllFavorites().collect { movieList ->
                _favoriteMovies.value = movieList
            }
        }
    }

    fun updateMovieNote(movieId: Int, note: String) {
        viewModelScope.launch {
            repository.updateMovieNote(movieId, note)
        }
    }

    fun deleteMovie(movie: FavoriteMovie) {
        viewModelScope.launch {
            repository.removeFavorite(movie)
        }
    }
}