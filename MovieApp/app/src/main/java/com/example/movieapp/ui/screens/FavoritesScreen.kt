package com.example.movieapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieapp.database.FavoriteMovie
import com.example.movieapp.viewmodel.FavoritesViewModel
import com.example.movieapp.viewmodel.MainViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    factory: MainViewModelFactory,
    onBack: () -> Unit,
    onMovieClick: (Int) -> Unit
) {
    val viewModel: FavoritesViewModel = viewModel(factory = factory)
    val favoriteMovies by viewModel.favoriteMovies.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Favoritos") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (favoriteMovies.isEmpty()) {
                item { Text("No tienes películas favoritas aún.") }
            } else {
                items(favoriteMovies) { movie ->
                    FavoriteItemWithEdit(
                        movie = movie,
                        onDeleteClick = {
                            viewModel.deleteMovie(movie)
                        },
                        onUpdateClick = { newNote ->

                            viewModel.updateMovieNote(movie.id, newNote)
                        },
                        onMovieClick = { onMovieClick(movie.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteItemWithEdit(
    movie: FavoriteMovie,
    onDeleteClick: () -> Unit,
    onUpdateClick: (String) -> Unit,
    onMovieClick: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var tempNote by remember { mutableStateOf(movie.userNote) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        onClick = onMovieClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(text = movie.title, style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(4.dp))

            if (movie.userNote.isNotEmpty()) {
                Text(
                    text = "Nota: ${movie.userNote}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            } else {
                Text(
                    text = "Sin nota personal...",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = {
                    tempNote = movie.userNote
                    showDialog = true
                }) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar nota")
                }

                IconButton(onClick = onDeleteClick) {
                    Icon(Icons.Default.Delete, contentDescription = "Borrar", tint = Color.Red)
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Editar nota personal") },
            text = {
                OutlinedTextField(
                    value = tempNote,
                    onValueChange = { tempNote = it },
                    label = { Text("¿Qué opinas de esta peli?") },
                    singleLine = false,
                    maxLines = 3
                )
            },
            confirmButton = {
                Button(onClick = {
                    onUpdateClick(tempNote)
                    showDialog = false
                }) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}