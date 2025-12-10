package com.example.levelup_gamer.remote

import com.example.levelup_gamer.modelo.ApiResponse
import com.example.levelup_gamer.modelo.PasswordChangeRequest
import com.example.levelup_gamer.modelo.Usuario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface UsuarioService {
    @PUT("perfil") // O la ruta que uses
    suspend fun updatePerfil(
        @Header("Authorization") token: String,
        @Body usuario: Usuario
    ): Response<ApiResponse<Usuario>>

    @GET("perfil")
    suspend fun getPerfil(
        @Header("Authorization") token: String
    ): Response<ApiResponse<Usuario>>

    @POST("change-password")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Body request: PasswordChangeRequest
    ): Response<ApiResponse<String>>
}