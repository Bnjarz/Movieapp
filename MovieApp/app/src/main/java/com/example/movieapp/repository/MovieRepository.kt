package com.example.movieapp.repository

import android.content.Context
import com.example.movieapp.database.AppDatabase
import com.example.movieapp.database.FavoriteMovie
import com.example.movieapp.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository(context: Context) {

    private val api = RetrofitInstance.api
    private val dao = AppDatabase.getDatabase(context).favoriteMovieDao()

    private val apiKey = "6764017497983147e9e1f5b87d5932f7" // 🔑 coloca aquí tu API key de TMDb

    // --- API (Internet) ---
    suspend fun getTrendingMovies(): List<Movie> =
        api.getTrendingMovies(apiKey).results

    suspend fun getNowPlayingMovies(): List<Movie> =
        api.getNowPlayingMovies(apiKey).results

    suspend fun getHorrorMovies(): List<Movie> =
        api.getHorrorMovies(apiKey).results

    suspend fun getRomanceMovies(): List<Movie> =
        api.getRomanceMovies(apiKey).results

    suspend fun getMovieDetail(movieId: Int): Movie =
        api.getMovieDetail(movieId, apiKey)

    // --- Base de Datos (Local) ---
    fun getAllFavorites(): Flow<List<FavoriteMovie>> = dao.getAllFavorites()

    fun isFavorite(movieId: Int): Flow<Boolean> =
        dao.isFavorite(movieId).map { it > 0 }

    suspend fun addFavorite(movie: Movie) {
        val favoriteMovie = FavoriteMovie(
            id = movie.id,
            title = movie.title,
            overview = movie.overview,
            posterPath = movie.posterPath ?: ""
        )
        dao.insertFavorite(favoriteMovie)
    }

    suspend fun addFavorite(favoriteMovie: FavoriteMovie) {
        dao.insertFavorite(favoriteMovie)
    }

    suspend fun removeFavorite(movie: Movie) {
        val favoriteMovie = FavoriteMovie(
            id = movie.id,
            title = movie.title,
            overview = movie.overview,
            posterPath = movie.posterPath ?: "" // ✅ cambio aquí también
        )
        dao.deleteFavorite(favoriteMovie)
    }

    suspend fun removeFavorite(favoriteMovie: FavoriteMovie) {
        dao.deleteFavorite(favoriteMovie)
    }
}
