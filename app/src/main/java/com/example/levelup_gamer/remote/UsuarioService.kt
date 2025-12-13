package com.example.levelup_gamer.remote

import com.example.levelup_gamer.modelo.Usuario
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface UsuarioService {
    @GET("/usuarios")
    suspend fun getAllUsuarios(): Response<List<Usuario>>

    @GET("/usuarios/{id}")
    suspend fun getUsuarioById(id: Int): Response<Usuario>

    @POST("/usuarios")
    suspend fun saveUsuario(@Body usuario: Usuario): Response<Usuario>

    @DELETE("/usuarios/{id}")
    suspend fun deleteUsuario(id: Int): Response<Unit>
}