package com.example.movieapp.data.auth

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")

data class UserData(
    val name: String,
    val email: String
)

class AuthPrefsRepo(private val context: Context) {

    companion object {
        val USER_ID_KEY = stringPreferencesKey("user_id")
        val USER_NAME_KEY = stringPreferencesKey("user_name")
        val USER_EMAIL_KEY = stringPreferencesKey("user_email")
    }

    suspend fun saveUserSession(name: String, email: String, userId: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = name
            preferences[USER_EMAIL_KEY] = email
            preferences[USER_ID_KEY] = userId
        }
        android.util.Log.d("AuthPrefsRepo", "Guardado: $name, $email")
    }

    suspend fun getUserData(): UserData? {
        val preferences = context.dataStore.data.first()

        val name = preferences[USER_NAME_KEY]
        val email = preferences[USER_EMAIL_KEY]

        android.util.Log.d("AuthPrefsRepo", "Leído: $name, $email")

        return if (name != null && email != null) {
            UserData(name, email)
        } else {
            null
        }
    }

    suspend fun clearCredentials() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}