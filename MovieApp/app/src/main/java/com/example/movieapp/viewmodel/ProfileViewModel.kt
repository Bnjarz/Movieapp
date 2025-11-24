package com.example.movieapp.viewmodel

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.auth.AuthPrefsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

class ProfileViewModel(
    private val context: Context
) : ViewModel() {

    private val authRepo = AuthPrefsRepo(context)

    private val _userName = MutableStateFlow("Cargando...")
    val userName = _userName.asStateFlow()

    private val _userEmail = MutableStateFlow("")
    val userEmail = _userEmail.asStateFlow()

    private val _profileImageUri = MutableStateFlow<Uri?>(null)
    val profileImageUri = _profileImageUri.asStateFlow()

    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            try {
                val userData = authRepo.getUserData()
                android.util.Log.d("ProfileViewModel", "userData: $userData")

                if (userData != null) {
                    _userName.value = userData.name
                    _userEmail.value = userData.email
                    android.util.Log.d("ProfileViewModel", "Nombre: ${userData.name}, Email: ${userData.email}")
                } else {
                    _userName.value = "Usuario desconocido"
                    _userEmail.value = "No disponible"
                    android.util.Log.d("ProfileViewModel", "userData es null")
                }
            } catch (e: Exception) {
                android.util.Log.e("ProfileViewModel", "Error cargando datos: ${e.message}")
                _userName.value = "Error al cargar"
                _userEmail.value = "Error"
            }
        }
    }

    fun updateProfileImage(uri: Uri?) {
        _profileImageUri.value = uri
    }

    fun createImageUri(context: Context): Uri {
        val directory = File(context.externalCacheDir, "images")
        directory.mkdirs()
        val file = File.createTempFile("selected_image_", ".jpg", directory)
        val authority = context.packageName + ".provider"
        return FileProvider.getUriForFile(context, authority, file)
    }

    fun logout(onSuccess: () -> Unit) {
        viewModelScope.launch {
            authRepo.clearCredentials()
            onSuccess()
        }
    }
}