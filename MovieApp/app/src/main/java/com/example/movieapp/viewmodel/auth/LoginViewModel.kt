package com.example.movieapp.viewmodel.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.util.Patterns
import android.content.Context
import com.example.movieapp.data.auth.AuthPrefsRepo

data class LoginFormState(
    val email: String = "",
    val contrasena: String = "",
    val isLoading: Boolean = false
)
data class LoginFormErrors(
    val emailError: String? = null,
    val contrasenaError: String? = null,
    val globalError: String? = null
)

class LoginViewModel(private val context: Context) : ViewModel() {
    private val _state = MutableStateFlow(LoginFormState())
    val state: StateFlow<LoginFormState> = _state.asStateFlow()
    private val _errors = MutableStateFlow(LoginFormErrors())
    val errors: StateFlow<LoginFormErrors> = _errors.asStateFlow()
    fun onEmailChange(v: String) { _state.value = _state.value.copy(email = v) }
    fun onContrasenaChange(v: String) { _state.value = _state.value.copy(contrasena = v) }

    private fun validateForm(): Boolean {
        val s = _state.value
        val emailErr = when {
            s.email.isBlank() -> "El correo es obligatorio"
            !Patterns.EMAIL_ADDRESS.matcher(s.email).matches() -> "Email inválido"
            else -> null
        }
        val contrasenaErr = if (s.contrasena.isBlank()) "La contraseña es obligatoria" else null

        _errors.value = LoginFormErrors(emailErr, contrasenaErr, null)
        return emailErr == null && contrasenaErr == null
    }

    fun onLoginClick(onSuccess: () -> Unit) {
        if (!validateForm()) return

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            try {

                val loginSuccessful = AuthPrefsRepo.verifyCredentials(
                    context = context,
                    email = _state.value.email,
                    passwordHash = _state.value.contrasena
                )

                if (loginSuccessful) {
                    onSuccess()
                    _state.value = LoginFormState()
                } else {
                    _errors.value = _errors.value.copy(globalError = "Credenciales incorrectas. Intenta de nuevo.")
                }
            } catch (e: Exception) {
                _errors.value = _errors.value.copy(globalError = "Error de sistema al verificar credenciales.")
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }
}

class LoginViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}