package com.example.levelup_gamer.repository

// ⚠️ IMPORTANTE: Fíjate que estamos importando desde 'modelo' y 'remote'
// que son las carpetas reales que tienes en tu proyecto.

import com.example.levelup_gamer.modelo.ApiResponse
import com.example.levelup_gamer.modelo.PasswordChangeRequest
import com.example.levelup_gamer.modelo.Usuario
import com.example.levelup_gamer.remote.UsuarioInstance
import com.example.levelup_gamer.remote.UsuarioService
import retrofit2.Response



// Definimos la clase con el servicio en el constructor (para que funcionen los Tests)
class UsuarioRepository(
    // Usamos 'UsuarioInstance.api' porque así se llama tu archivo de conexión real
    private val apiService: UsuarioService = UsuarioInstance.api
) {

    // 1. Función para actualizar perfil (o registrar)
    suspend fun updatePerfil(token: String, usuario: Usuario): Response<ApiResponse<Usuario>> {
        return apiService.updatePerfil(token, usuario)
    }

    // 2. Función para obtener datos del perfil
    suspend fun getPerfil(token: String): Response<ApiResponse<Usuario>> {
        return apiService.getPerfil(token)
    }

    // 3. Función para cambiar la contraseña
    suspend fun changePassword(token: String, request: PasswordChangeRequest): Response<ApiResponse<String>> {
        return apiService.changePassword(token, request)
    }
}