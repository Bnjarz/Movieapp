package com.example.movieapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(movie: FavoriteMovie)

    @Delete
    suspend fun deleteFavorite(movie: FavoriteMovie)


    @Query("SELECT * FROM favorite_movies")
    fun getAllFavorites(): Flow<List<FavoriteMovie>>

    @Query("SELECT COUNT(id) FROM favorite_movies WHERE id = :movieId")
    fun isFavorite(movieId: Int): Flow<Int>
}