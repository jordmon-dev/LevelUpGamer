// UsuarioInstance.kt en /remote/
package com.example.levelup_gamer.remote

import android.content.Context
import android.content.SharedPreferences

object UsuarioInstance {
    private lateinit var prefs: SharedPreferences
    private const val PREFS_NAME = "usuario_prefs"
    private const val KEY_TOKEN = "auth_token"
    private const val KEY_EMAIL = "user_email"

    fun initialize(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveToken(token: String) {
        prefs.edit().putString(KEY_TOKEN, token).apply()
    }

    fun getToken(): String? {
        return prefs.getString(KEY_TOKEN, null)
    }

    fun saveUserEmail(email: String) {
        prefs.edit().putString(KEY_EMAIL, email).apply()
    }

    fun getUserEmail(): String? {
        return prefs.getString(KEY_EMAIL, null)
    }

    fun isAuthenticated(): Boolean {
        return getToken() != null
    }

    fun logout(context: Context) {
        prefs.edit().clear().apply()
    }
}