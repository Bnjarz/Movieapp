// En: database/FavoriteMovieDao.kt
package com.example.movieapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {

    // Inserta una peli. Si ya existe (mismo 'id'), la reemplaza
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(movie: FavoriteMovie)

    // Borra una peli
    @Delete
    suspend fun deleteFavorite(movie: FavoriteMovie)


    @Query("SELECT * FROM favorite_movies")
    fun getAllFavorites(): Flow<List<FavoriteMovie>>

    // Revisa si una película (por su id) ya está en favoritos
    // Devuelve 1 (si es 'true') o 0 (si es 'false')
    @Query("SELECT COUNT(id) FROM favorite_movies WHERE id = :movieId")
    fun isFavorite(movieId: Int): Flow<Int>
}