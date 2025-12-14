package com.example.levelup_gamer.repository

import com.example.levelup_gamer.model.AuthResponse
import com.example.levelup_gamer.model.LoginDto
import com.example.levelup_gamer.model.RegistroDto
import com.example.levelup_gamer.remote.RetrofitInstance
import retrofit2.Response

class UsuarioRepository {

    // Instancia de Retrofit (La conexión a Spring Boot)
    // Asegúrate de que RetrofitInstance.api devuelva LevelUpApiService
    private val api = RetrofitInstance.api

    // --- LOGIN ---
    suspend fun login(email: String, pass: String): Response<AuthResponse> {
        val loginDto = LoginDto(email = email, password = pass)
        return api.login(loginDto)
    }

    // --- REGISTRO ---
    suspend fun registrar(nombre: String, email: String, pass: String): Response<Any> {
        // Creamos el DTO exacto que tu Backend Java espera
        val registroDto = RegistroDto(
            nombre = nombre,
            apellidos = "", // Enviamos vacío porque la UI no lo pide
            email = email,
            password = pass,
            direccion = "Sin dirección", // Valores por defecto
            region = "Metropolitana",
            comuna = "Santiago"
        )
        return api.registrar(registroDto)
    }
}