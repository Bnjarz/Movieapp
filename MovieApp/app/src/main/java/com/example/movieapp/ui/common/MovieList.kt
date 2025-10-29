// En: ui/common/MovieList.kt
package com.example.movieapp.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movieapp.model.Movie

@Composable
fun MovieList(
    title: String,
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit // 🔹 función que recibe un movieId (Int)
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = title, modifier = Modifier.padding(horizontal = 16.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(movies) { movie ->
                MovieCard(
                    posterUrl = movie.posterPath ?: "",
                    title = movie.title,
                    onClick = { onMovieClick(movie.id) }
                )
            }
        }
    }
}
