package com.example.levelup_gamer.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class UserPreferences(private val context: Context) {

    // KEYS
    companion object {
        private val KEY_LOGGED = booleanPreferencesKey("logged")
        private val KEY_NOMBRE = stringPreferencesKey("nombre")
        private val KEY_PASSWORD = stringPreferencesKey("password")

        // Foto de perfil
        private val KEY_FOTO_PERFIL = stringPreferencesKey("foto_perfil")
    }

    // ------------------------------
    // GUARDAR CREDENCIALES
    // ------------------------------
    suspend fun saveCredentials(nombre: String, password: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_LOGGED] = true
            prefs[KEY_NOMBRE] = nombre
            prefs[KEY_PASSWORD] = password
        }
    }

    // ------------------------------
    // CERRAR SESIÓN
    // ------------------------------
    suspend fun logout() {
        context.dataStore.edit { prefs ->
            prefs[KEY_LOGGED] = false
            prefs.remove(KEY_NOMBRE)
            prefs.remove(KEY_PASSWORD)
            // NO borramos foto si no quieres
        }
    }

    // ------------------------------
    // GETTERS (Flows)
    // ------------------------------
    val isLogged: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[KEY_LOGGED] ?: false
    }

    val usuario: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[KEY_NOMBRE] ?: ""
    }

    val password: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[KEY_PASSWORD] ?: ""
    }

    // ------------------------------
    // FOTO DE PERFIL
    // ------------------------------
    suspend fun saveProfilePhoto(uri: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_FOTO_PERFIL] = uri
        }
    }

    val fotoPerfil: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[KEY_FOTO_PERFIL]
    }

    suspend fun clearProfilePhoto() {
        context.dataStore.edit { prefs ->
            prefs.remove(KEY_FOTO_PERFIL)
        }
    }
}
