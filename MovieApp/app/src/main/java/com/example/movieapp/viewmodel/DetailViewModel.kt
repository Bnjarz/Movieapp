
package com.example.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.database.FavoriteMovie
import com.example.movieapp.model.Movie
import com.example.movieapp.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _movie = MutableStateFlow<Movie?>(null)
    val movie: StateFlow<Movie?> = _movie

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    fun loadMovie(movieId: Int) {
        viewModelScope.launch {

            _movie.value = repository.getMovieDetail(movieId)

            _isFavorite.value = repository.isFavorite(movieId).first()
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            val currentMovie = _movie.value
            if (currentMovie != null) {
                if (_isFavorite.value) {

                    repository.removeFavorite(currentMovie)
                } else {

                    repository.addFavorite(currentMovie)
                }

                _isFavorite.value = !_isFavorite.value
            }
        }
    }
}