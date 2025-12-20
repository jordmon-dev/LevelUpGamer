package com.example.levelup_gamer.remote

import com.example.levelup_gamer.model.AuthResponse
import com.example.levelup_gamer.model.Favorito
import com.example.levelup_gamer.model.FavoritoDto
import com.example.levelup_gamer.model.LoginDto
import com.example.levelup_gamer.model.Orden
import com.example.levelup_gamer.model.Producto
import com.example.levelup_gamer.model.RegistroDto
import retrofit2.Response
import retrofit2.http.*

interface LevelUpApiService {

    // --- AUTENTICACIÓN ---
    @POST("auth/login")
    suspend fun login(@Body loginDto: LoginDto): Response<AuthResponse>

    @POST("auth/register")
    suspend fun registrar(@Body registroDto: RegistroDto): Response<Any>

    // --- ÓRDENES ---
    @POST("/api/v1/ordenes")
    suspend fun crearOrden(@Body orden: Orden): Response<Orden>

    // Si implementaste la pantalla de ventas globales:
    @GET("/api/v1/ordenes")
    suspend fun obtenerOrdenes(@Header("Authorization") token: String): Response<List<Orden>>

    // --- PRODUCTOS ---

    // 1. Listar (Público, no requiere token)
    @GET("productos")
    suspend fun obtenerProductos(): Response<List<Producto>>

    // 2. Obtener uno (Público)
    @GET("productos/{id}")
    suspend fun obtenerProductoPorId(@Path("id") id: Long): Response<Producto>

    // 3. CREAR (Privado, REQUIERE TOKEN)
    @POST("productos")
    suspend fun crearProducto(
        @Header("Authorization") token: String, // ✅ AGREGADO
        @Body producto: Producto
    ): Response<Producto>

    // 4. ELIMINAR (Privado, REQUIERE TOKEN)
    @DELETE("productos/{id}")
    suspend fun eliminarProducto(
        @Header("Authorization") token: String, // ✅ AGREGADO
        @Path("id") id: Long
    ): Response<Void>

    // --- SECCIÓN DE FAVORITOS ---

    //Ver favoritos
    @GET("api/v1/favoritos/{email")
    suspend fun obtenerFavoritos(@Path("email") email: String): Response<List<Favorito>>

    //Para dar o quitar like
    @POST("api/v1/favoritos/toggle")
    suspend fun toggleFavorito(@Body dto: FavoritoDto): Response<Void>

}