package com.example.levelup_gamer.remote

import com.example.levelup_gamer.model.AuthResponse
import com.example.levelup_gamer.model.LoginDto
import com.example.levelup_gamer.model.Producto
import com.example.levelup_gamer.model.RegistroDto // Asegúrate de tener este modelo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LevelUpApiService {

    // --- AUTH ---
    @POST("auth/login")
    suspend fun login(@Body loginDto: LoginDto): Response<AuthResponse>

    // Nota: Tu backend espera RegistroDto, no Usuario directo
    @POST("auth/register")
    suspend fun registrar(@Body registroDto: RegistroDto): Response<Any>

    // --- PRODUCTOS ---
    @GET("productos")
    suspend fun obtenerProductos(): Response<List<Producto>>
}