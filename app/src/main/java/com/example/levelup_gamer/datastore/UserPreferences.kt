package com.example.levelup_gamer.datastore


import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences(private val context: Context) {

    companion object {
        val SESION_INICIADA = booleanPreferencesKey("sesion_iniciada")
        val CORREO = stringPreferencesKey("correo")
        val PASSWORD = stringPreferencesKey("password")
    }

    val sesionIniciada: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[SESION_INICIADA] ?: false
    }

    val correo: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[CORREO] ?: ""
    }

    val password: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[PASSWORD] ?: ""
    }

    suspend fun guardarSesion(correo: String, password: String) {
        context.dataStore.edit { prefs ->
            prefs[SESION_INICIADA] = true
            prefs[CORREO] = correo
            prefs[PASSWORD] = password
        }
    }

    suspend fun cerrarSesion() {
        context.dataStore.edit { prefs ->
            prefs[SESION_INICIADA] = false
            prefs[CORREO] = ""
            prefs[PASSWORD] = ""
        }
    }
}
