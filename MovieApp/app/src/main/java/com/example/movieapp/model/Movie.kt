package com.example.movieapp.model

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("release_date")
    val releaseDate: String?,

    @SerializedName("vote_average")
    val voteAverage: Double?
) {
    fun getPosterUrl(): String {
        return if (posterPath != null) {
            "https://image.tmdb.org/t/p/w500$posterPath"
        } else {
            "https://via.placeholder.com/500x750?text=No+Image"
        }
    }
}

data class MovieApiResponse(
    val page: Int,
    val results: List<Movie>
)
