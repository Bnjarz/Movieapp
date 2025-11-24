package com.example.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.movieapp.data.auth.AuthPrefsRepo
import com.example.movieapp.repository.MovieRepository
import com.example.movieapp.ui.navigation.MainNavigation
import com.example.movieapp.ui.theme.MovieAppTheme
import com.example.movieapp.viewmodel.MainViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository = MovieRepository(applicationContext)

        val factory = MainViewModelFactory(repository, applicationContext)

        setContent {
            MovieAppTheme {
                MainNavigation(factory)
            }
        }
    }
}