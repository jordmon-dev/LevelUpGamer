package com.example.levelup_gamer.repository

import com.example.levelup_gamer.model.AuthResponse
import com.example.levelup_gamer.model.LoginDto
import com.example.levelup_gamer.model.RegistroDto
import com.example.levelup_gamer.remote.RetrofitInstance
import retrofit2.Response

class UsuarioRepository {

    // Conectamos con la instancia corregida
    private val api = RetrofitInstance.api

    suspend fun login(email: String, pass: String): Response<AuthResponse> {
        return api.login(LoginDto(email, pass))
    }

    suspend fun register(nombre: String, email: String, pass: String): Response<Any> {
        // Creamos el DTO que espera el backend
        val registroDto = RegistroDto(
            nombre = nombre,
            apellidos = "", // Campos requeridos por backend pero no por UI
            email = email,
            password = pass,
            direccion = "Sin dirección",
            region = "RM",
            comuna = "Santiago"
        )
        return api.registrar(registroDto)
    }
}