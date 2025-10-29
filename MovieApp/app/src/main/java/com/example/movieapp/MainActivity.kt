package com.example.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.movieapp.ui.navigation.MainNavigation
import com.example.movieapp.viewmodel.MainViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Usa application (no applicationContext)
        val factory = MainViewModelFactory(application)

        setContent {
            MainNavigation(factory)
        }
    }
}
