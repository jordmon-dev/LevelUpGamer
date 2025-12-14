package com.example.levelup_gamer.remote

import com.example.levelup_gamer.model.ApiResponse
import com.example.levelup_gamer.model.PasswordChangeRequest
import com.example.levelup_gamer.model.Usuario
import retrofit2.Response
import retrofit2.http.*

interface UsuarioService {

    @GET("api/usuarios/perfil")
    suspend fun getPerfil(
        @Header("Authorization") token: String
    ): Response<ApiResponse<Usuario>>

    @PUT("api/usuarios/perfil")
    suspend fun updatePerfil(
        @Header("Authorization") token: String,
        @Body usuario: Usuario
    ): Response<ApiResponse<Usuario>>

    @PUT("api/usuarios/cambiar-password")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Body passwordRequest: PasswordChangSeRequest
    ): Response<ApiResponse<String>>
}