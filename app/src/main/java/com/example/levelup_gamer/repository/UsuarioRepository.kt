package com.example.levelup_gamer.repository

import android.util.Log
import com.example.levelup_gamer.modelo.Usuario
import com.example.levelup_gamer.remote.RetrofitInstance

class UsuarioRepository {
    private val ApiService = RetrofitInstance.api

    suspend fun mostrarTodosLosUsuarios(): List<Usuario>? {
        val response = ApiService.getAllUsuarios()
        Log.d("UsuarioRepository", "Response: $response")
        return if (response.isSuccessful) {
            return response.body()
        } else {
            emptyList()
        }
    }

    suspend fun agregarUsuario(usuario: Usuario): Boolean {
        val response = ApiService.saveUsuario(usuario)
        if (response.isSuccessful) {
            return true
        } else {
            return false
        }
    }

    suspend fun buscarUsuarioPorId(id: Int): Usuario? {
        val response = ApiService.getUsuarioById(id)
        return if (response.isSuccessful) {
            response.body()
        } else {
            emptyList()
        }
    }

    suspend fun eliminarUsuario(id: Int): Boolean {
        val response = ApiService.deleteUsuario(id)
        return response.isSuccessful
    }
})
}