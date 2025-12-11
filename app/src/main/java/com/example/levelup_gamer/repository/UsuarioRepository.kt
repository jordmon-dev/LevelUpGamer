package com.example.levelup_gamer.repository

import com.example.levelup_gamer.modelo.Usuario
import com.example.levelup_gamer.remote.RetrofitInstance
import com.example.levelup_gamer.remote.UsuarioApiService

class UsuarioRepository {
    private val usuarioApiService: UsuarioApiService = RetrofitInstance.usuarioApiService

    suspend fun login(email: String, password: String): Result<Usuario> {
        return try {
            val response = usuarioApiService.login(mapOf("email" to email, "password" to password))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Error en el login"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(nombre: String, email: String, password: String): Result<Usuario> {
        return try {
            val usuario = Usuario(nombreCompleto = nombre, email = email, password = password)
            val response = usuarioApiService.register(usuario)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Error en el registro"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getPerfil(email: String): Result<Usuario> {
        return try {
            val response = usuarioApiService.getUsuarioPorEmail(email)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Error al obtener perfil"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logout(): Result<Unit> {
        return try {
            val response = usuarioApiService.logout()
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Error al cerrar sesión"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
