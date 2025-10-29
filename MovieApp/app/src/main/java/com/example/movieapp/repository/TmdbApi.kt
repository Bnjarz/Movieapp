package com.example.movieapp.repository

import com.example.movieapp.model.Movie
import com.example.movieapp.model.MovieApiResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("api_key") apiKey: String
    ): MovieApiResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String
    ): MovieApiResponse

    @GET("discover/movie")
    suspend fun getHorrorMovies(
        @Query("api_key") apiKey: String,
        @Query("with_genres") genreId: Int = 27
    ): MovieApiResponse

    @GET("discover/movie")
    suspend fun getRomanceMovies(
        @Query("api_key") apiKey: String,
        @Query("with_genres") genreId: Int = 10749
    ): MovieApiResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Movie
}

// --- Objeto Retrofit para inicializar la API ---
object RetrofitInstance {
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    val api: TmdbApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TmdbApi::class.java)
    }
}
