package com.example.levelup_gamer.remote

import com.example.levelup_gamer.modelo.Usuario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UsuarioApiService {

    // ============ AUTENTICACIÓN ============

    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: Map<String, String> // {"email": "...", "password": "..."}
    ): Response<Usuario>

    @POST("auth/register")
    suspend fun register(
        @Body usuario: Usuario
    ): Response<Usuario>

    @POST("auth/logout")
    suspend fun logout(): Response<Void>

    // ============ OPERACIONES CRUD ============

    @GET("usuarios/{email}")
    suspend fun getUsuarioPorEmail(
        @Path("email") email: String
    ): Response<Usuario>

    @GET("usuarios/{id}")
    suspend fun getUsuarioPorId(
        @Path("id") id: Int
    ): Response<Usuario>

    @PUT("usuarios/{id}")
    suspend fun actualizarUsuario(
        @Path("id") id: Int,
        @Body usuario: Usuario
    ): Response<Usuario>

    @PUT("usuarios/{id}/perfil")
    suspend fun actualizarPerfil(
        @Path("id") id: Int,
        @Body perfilData: Map<String, String> // {"nombre": "...", "telefono": "..."}
    ): Response<Usuario>

    @PUT("usuarios/{id}/password")
    suspend fun cambiarPassword(
        @Path("id") id: Int,
        @Body passwordData: Map<String, String> // {"oldPassword": "...", "newPassword": "..."}
    ): Response<Void>

    // ============ OPERACIONES ESPECÍFICAS ============

    @GET("usuarios/verificar-email")
    suspend fun verificarEmailDisponible(
        @Query("email") email: String
    ): Response<Boolean> // true si está disponible

    @POST("usuarios/{id}/avatar")
    suspend fun subirAvatar(
        @Path("id") id: Int,
        @Body avatarData: Map<String, String> // {"avatarUrl": "..."}
    ): Response<Usuario>

    @GET("usuarios/{id}/preferencias")
    suspend fun getPreferenciasUsuario(
        @Path("id") id: Int
    ): Response<List<String>>

    @PUT("usuarios/{id}/preferencias")
    suspend fun actualizarPreferencias(
        @Path("id") id: Int,
        @Body preferencias: List<String>
    ): Response<Void>

    // ============ ADMINISTRADOR (opcional) ============

    @GET("admin/usuarios")
    suspend fun getAllUsuarios(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<List<Usuario>>

    @PUT("admin/usuarios/{id}/estado")
    suspend fun cambiarEstadoUsuario(
        @Path("id") id: Int,
        @Body estadoData: Map<String, Boolean> // {"activo": true/false}
    ): Response<Usuario>

    @PUT("admin/usuarios/{id}/rol")
    suspend fun cambiarRolUsuario(
        @Path("id") id: Int,
        @Body rolData: Map<String, String> // {"rol": "admin/cliente"}
    ): Response<Usuario>
}