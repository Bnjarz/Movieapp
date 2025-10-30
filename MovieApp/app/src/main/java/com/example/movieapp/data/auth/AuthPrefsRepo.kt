package com.example.movieapp.data.auth

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val KEY_USER_EMAIL = stringPreferencesKey("user_email")
private val KEY_USER_PASS_HASH = stringPreferencesKey("user_pass_hash")

val Context.dataStore by preferencesDataStore(name = "auth_prefs")

object AuthPrefsRepo {


    suspend fun saveCredentials(context: Context, email: String, passwordHash: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_USER_EMAIL] = email
            prefs[KEY_USER_PASS_HASH] = passwordHash
        }
    }

    suspend fun verifyCredentials(context: Context, email: String, passwordHash: String): Boolean {

        val savedPrefs = context.dataStore.data.first()
        val savedEmail = savedPrefs[KEY_USER_EMAIL]
        val savedPassHash = savedPrefs[KEY_USER_PASS_HASH]

        return savedEmail == email && savedPassHash == passwordHash
    }
    suspend fun clearCredentials(context: Context) {
        context.dataStore.edit { prefs ->
            prefs.remove(KEY_USER_EMAIL)
            prefs.remove(KEY_USER_PASS_HASH)
        }
    }
    suspend fun hasCredentials(context: Context): Boolean {
        val savedPrefs = context.dataStore.data.first()
        val savedEmail = savedPrefs[KEY_USER_EMAIL]
        val savedPassHash = savedPrefs[KEY_USER_PASS_HASH]
        return savedEmail != null && savedPassHash != null
    }
}