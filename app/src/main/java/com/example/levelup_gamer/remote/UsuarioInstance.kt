package com.example.levelup_gamer.remote

import android.content.Context
import android.content.SharedPreferences
import com.example.levelup_gamer.model.Usuario
import com.google.gson.Gson

object UsuarioInstance {

    // Variables para almacenar datos en memoria
    private var currentUsuario: Usuario? = null
    private var authToken: String? = null
    private var isLoggedIn: Boolean = false

    // Constantes para SharedPreferences
    private const val PREFS_NAME = "usuario_prefs"
    private const val KEY_USUARIO = "current_usuario"
    private const val KEY_TOKEN = "auth_token"
    private const val KEY_IS_LOGGED_IN = "is_logged_in"
    private const val KEY_REFRESH_TOKEN = "refresh_token"

    /**
     * Inicializa la instancia desde SharedPreferences
     */
    fun initialize(context: Context) {
        val prefs = getSharedPreferences(context)

        // Cargar token
        authToken = prefs.getString(KEY_TOKEN, null)

        // Cargar usuario
        val usuarioJson = prefs.getString(KEY_USUARIO, null)
        usuarioJson?.let {
            currentUsuario = Gson().fromJson(it, Usuario::class.java)
        }

        // Cargar estado de login
        isLoggedIn = prefs.getBoolean(KEY_IS_LOGGED_IN, false) && authToken != null
    }

    /**
     * Guarda los datos del usuario después del login
     */
    fun saveUser(context: Context, usuario: Usuario, token: String, refreshToken: String? = null) {
        val prefs = getSharedPreferences(context)
        val editor = prefs.edit()

        // Guardar usuario como JSON
        val usuarioJson = Gson().toJson(usuario)
        editor.putString(KEY_USUARIO, usuarioJson)

        // Guardar tokens
        editor.putString(KEY_TOKEN, token)
        refreshToken?.let {
            editor.putString(KEY_REFRESH_TOKEN, it)
        }

        // Guardar estado
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.apply()

        // Actualizar en memoria
        currentUsuario = usuario
        authToken = token
        isLoggedIn = true
    }

    /**
     * Actualiza el perfil del usuario
     */
    fun updateProfile(context: Context, usuario: Usuario) {
        val prefs = getSharedPreferences(context)
        val editor = prefs.edit()

        // Actualizar usuario
        val usuarioJson = Gson().toJson(usuario)
        editor.putString(KEY_USUARIO, usuarioJson)
        editor.apply()

        // Actualizar en memoria
        currentUsuario = usuario
    }

    /**
     * Actualiza solo el token
     */
    fun updateToken(context: Context, newToken: String) {
        val prefs = getSharedPreferences(context)
        val editor = prefs.edit()

        editor.putString(KEY_TOKEN, newToken)
        editor.apply()

        authToken = newToken
    }

    /**
     * Cierra la sesión del usuario
     */
    fun logout(context: Context) {
        val prefs = getSharedPreferences(context)
        val editor = prefs.edit()

        editor.clear()
        editor.apply()

        // Limpiar memoria
        clear()
    }

    /**
     * Obtiene el token con formato Bearer
     */
    fun getBearerToken(): String? {
        return authToken?.let { "Bearer $it" }
    }

    /**
     * Obtiene el token sin formato
     */
    fun getToken(): String? {
        return authToken
    }

    /**
     * Obtiene el usuario actual
     */
    fun getCurrentUser(): Usuario? {
        return currentUsuario
    }

    /**
     * Verifica si hay un usuario autenticado
     */
    fun isAuthenticated(): Boolean {
        return isLoggedIn && authToken != null && currentUsuario != null
    }

    /**
     * Obtiene el ID del usuario actual
     */
    fun getUserId(): Long? {
        return currentUsuario?.id
    }

    /**
     * Obtiene el email del usuario actual
     */
    fun getUserEmail(): String? {
        return currentUsuario?.email
    }

    /**
     * Obtiene el nombre del usuario actual
     */
    fun getUserName(): String? {
        return currentUsuario?.nombre
    }

    /**
     * Limpia todos los datos en memoria
     */
    private fun clear() {
        currentUsuario = null
        authToken = null
        isLoggedIn = false
    }

    /**
     * Obtiene las SharedPreferences
     */
    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }
}