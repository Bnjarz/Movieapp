// En: ui/navigation/BottomNavItem.kt
package com.example.movieapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

// Usamos una 'sealed class' para definir las rutas y los iconos
sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Home : BottomNavItem("home", Icons.Default.Home, "Inicio")
    object Favorites : BottomNavItem("favorites", Icons.Default.Favorite, "Favoritos")
}