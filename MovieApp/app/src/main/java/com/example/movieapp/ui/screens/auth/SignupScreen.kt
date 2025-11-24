package com.example.movieapp.ui.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieapp.viewmodel.MainViewModelFactory
import com.example.movieapp.viewmodel.auth.SignupViewModel

@Composable
fun SignupScreen(
    navController: NavController,
    factory: MainViewModelFactory
) {
    val viewModel: SignupViewModel = viewModel(factory = factory)

    val state by viewModel.state.collectAsState()
    val errors by viewModel.errors.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Crear Cuenta", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = state.nombreCompleto, // Usamos tu estado
            onValueChange = { viewModel.onNombreCompletoChange(it) }, // Usamos tu función
            label = { Text("Nombre completo") },
            modifier = Modifier.fillMaxWidth(),
            isError = errors.nombreCompletoError != null,
            singleLine = true
        )
        if (errors.nombreCompletoError != null) {
            Text(text = errors.nombreCompletoError!!, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth(),
            isError = errors.emailError != null,
            singleLine = true
        )
        if (errors.emailError != null) {
            Text(text = errors.emailError!!, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.contrasena,
            onValueChange = { viewModel.onContrasenaChange(it) },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            isError = errors.contrasenaError != null,
            singleLine = true
        )
        if (errors.contrasenaError != null) {
            Text(text = errors.contrasenaError!!, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (errors.globalError != null) {
            Text(
                text = errors.globalError!!,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Button(
            onClick = {
                viewModel.onSignupClick {
                    navController.navigate("home") {
                        popUpTo("welcome") { inclusive = true }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
            } else {
                Text("Registrarse")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { navController.popBackStack() }) {
            Text("¿Ya tienes cuenta? Inicia sesión")
        }
    }
}