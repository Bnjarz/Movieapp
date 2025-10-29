// En: ui/common/MovieCard.kt
package com.example.movieapp.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun MovieCard(
    posterUrl: String,
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    AsyncImage(
        model = if (posterUrl.isNotEmpty())
            "https://image.tmdb.org/t/p/w500$posterUrl"
        else
            "https://via.placeholder.com/500x750?text=No+Image",
        contentDescription = title,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .width(150.dp)
            .aspectRatio(2 / 3f)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
    )
}
