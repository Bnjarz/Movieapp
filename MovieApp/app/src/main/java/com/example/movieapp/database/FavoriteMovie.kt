// En: database/FavoriteMovie.kt
package com.example.movieapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies") // Nombre de la tabla
data class FavoriteMovie(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String
)