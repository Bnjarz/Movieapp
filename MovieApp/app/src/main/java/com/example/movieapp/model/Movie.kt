package com.example.movieapp.model

import com.google.gson.annotations.SerializedName

// 🟦 Modelo principal de una película
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,

    // 🔽 Campos del JSON de TMDB
    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("release_date")
    val releaseDate: String?,

    @SerializedName("vote_average")
    val voteAverage: Double?
) {
    // 🔽 Devuelve la URL completa del póster (o un placeholder si falta)
    fun getPosterUrl(): String {
        return if (posterPath != null) {
            "https://image.tmdb.org/t/p/w500$posterPath"
        } else {
            "https://via.placeholder.com/500x750?text=No+Image"
        }
    }
}

// 🟦 Respuesta de la API (lista de películas)
data class MovieApiResponse(
    val page: Int,
    val results: List<Movie>
)
