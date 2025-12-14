package com.example.levelup_gamer.repository

import com.example.levelup_gamer.model.AuthResponse
import com.example.levelup_gamer.model.LoginDto
import com.example.levelup_gamer.model.RegistroDto
import com.example.levelup_gamer.remote.RetrofitInstance
import retrofit2.Response

class UsuarioRepository {

    private val api = RetrofitInstance.api

    // Login real contra Spring Boot
    suspend fun login(email: String, pass: String): Response<AuthResponse> {
        return api.login(LoginDto(email, pass))
    }

    // Registro real
    suspend fun registrar(nombre: String, email: String, pass: String): Response<Any> {
        val registroDto = RegistroDto(
            nombre = nombre,
            apellidos = "",
            email = email,
            password = pass,
            direccion = "Sin dirección",
            region = "RM",
            comuna = "Santiago"
        )
        return api.registrar(registroDto)
    }
}