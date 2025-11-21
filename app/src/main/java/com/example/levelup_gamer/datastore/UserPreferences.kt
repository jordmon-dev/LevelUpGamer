// UserPreferences.kt - Agrega estas funciones
package com.example.levelup_gamer.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class UserPreferences(private val context: Context) {

    companion object {
        private val SESION_INICIADA = booleanPreferencesKey("sesion_iniciada")
        private val USER_EMAIL = stringPreferencesKey("user_email")
        private val USER_PASSWORD = stringPreferencesKey("user_password")
        // NUEVO: Key para la foto de perfil
        private val FOTO_PERFIL_URI = stringPreferencesKey("foto_perfil_uri")
    }

    // Función existente para guardar sesión
    suspend fun guardarSesion(email: String, password: String) {
        context.dataStore.edit { preferences ->
            preferences[SESION_INICIADA] = true
            preferences[USER_EMAIL] = email
            preferences[USER_PASSWORD] = password
        }
    }

    // Función existente para cerrar sesión
    suspend fun cerrarSesion() {
        context.dataStore.edit { preferences ->
            preferences[SESION_INICIADA] = false
            preferences.remove(USER_EMAIL)
            preferences.remove(USER_PASSWORD)
            // Opcional: también limpiar la foto al cerrar sesión
            // preferences.remove(FOTO_PERFIL_URI)
        }
    }

    // Flow existente para sesión iniciada
    val sesionIniciada: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[SESION_INICIADA] ?: false
        }

    // NUEVAS FUNCIONES PARA FOTO DE PERFIL
    suspend fun guardarFotoPerfil(uri: String) {
        context.dataStore.edit { preferences ->
            preferences[FOTO_PERFIL_URI] = uri
        }
    }

    suspend fun limpiarFotoPerfil() {
        context.dataStore.edit { preferences ->
            preferences.remove(FOTO_PERFIL_URI)
        }
    }

    val fotoPerfil: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[FOTO_PERFIL_URI]
        }
}