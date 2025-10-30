package com.example.movieapp.viewmodel.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.util.Patterns
import android.content.Context
import com.example.movieapp.data.auth.AuthPrefsRepo
data class SignupFormState(
    val nombreCompleto: String = "",
    val email: String = "",
    val contrasena: String = "",
    val isLoading: Boolean = false
)


data class SignupFormErrors(
    val nombreCompletoError: String? = null,
    val emailError: String? = null,
    val contrasenaError: String? = null,
    val globalError: String? = null
)

class SignupViewModel(private val context: Context) : ViewModel() {
    private val _state = MutableStateFlow(SignupFormState())
    val state: StateFlow<SignupFormState> = _state.asStateFlow()
    private val _errors = MutableStateFlow(SignupFormErrors())
    val errors: StateFlow<SignupFormErrors> = _errors.asStateFlow()



    fun onNombreCompletoChange(v: String) {
        _state.value = _state.value.copy(nombreCompleto = v)
        if (_errors.value.nombreCompletoError != null) {
            _errors.value = _errors.value.copy(nombreCompletoError = null)
        }
    }

    fun onEmailChange(v: String) {
        _state.value = _state.value.copy(email = v)

        if (_errors.value.emailError != null) {
            _errors.value = _errors.value.copy(emailError = null)
        }
    }

    fun onContrasenaChange(v: String) {
        _state.value = _state.value.copy(contrasena = v)

        if (_errors.value.contrasenaError != null) {
            _errors.value = _errors.value.copy(contrasenaError = null)
        }
    }

    private fun validateForm(): Boolean {
        val s = _state.value

        val nombreErr = if (s.nombreCompleto.isBlank()) {
            "El nombre completo es obligatorio"
        } else if (s.nombreCompleto.length < 3) {
            "El nombre es demasiado corto"
        } else null

        val emailErr = when {
            s.email.isBlank() -> "El correo es obligatorio"
            !Patterns.EMAIL_ADDRESS.matcher(s.email).matches() -> "El formato del correo es inválido"
            else -> null
        }

        val contrasenaErr = when {
            s.contrasena.isBlank() -> "La contraseña es obligatoria"
            s.contrasena.length < 6 -> "La contraseña debe tener al menos 6 caracteres"
            else -> null
        }

        _errors.value = SignupFormErrors(
            nombreCompletoError = nombreErr,
            emailError = emailErr,
            contrasenaError = contrasenaErr,
            globalError = null
        )

        return listOf(nombreErr, emailErr, contrasenaErr).all { it == null }
    }

    fun onSignupClick(onSuccess: () -> Unit) {
        if (!validateForm()) {
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            try {
                AuthPrefsRepo.saveCredentials(
                    context = context,
                    email = _state.value.email,
                    passwordHash = _state.value.contrasena
                )
                onSuccess()
                resetState()

            } catch (e: Exception) {

                _errors.value = _errors.value.copy(globalError = "Error al guardar datos. Inténtalo de nuevo.")
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }


    fun resetState() {
        _state.value = SignupFormState()
        _errors.value = SignupFormErrors()
    }
}

class SignupViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignupViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SignupViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}