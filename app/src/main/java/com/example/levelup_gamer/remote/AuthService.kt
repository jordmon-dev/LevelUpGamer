package com.example.levelup_gamer.remote

import com.example.levelup_gamer.model.LoginDto
import com.example.levelup_gamer.model.RegistroDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    // Login: POST http://10.0.2.2:8080/api/v1/auth/login
    // Tu backend devuelve un Map con "token" y "usuario", por eso usamos Map<String, Any>
    // O podrías crear una data class AuthResponse si prefieres más orden.
    @POST("auth/login")
    suspend fun login(@Body loginDto: LoginDto): Response<Map<String, Any>>

    // Registro: POST http://10.0.2.2:8080/api/v1/auth/register
    @POST("auth/register")
    suspend fun registrar(@Body registroDto: RegistroDto): Response<Any>
}