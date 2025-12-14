package com.example.levelup_gamer.remote

import com.example.levelup_gamer.model.AuthResponse
import com.example.levelup_gamer.model.LoginDto
import com.example.levelup_gamer.model.Producto
import com.example.levelup_gamer.model.RegistroDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface LevelUpApiService {

    // ==========================================
    // 🔐 AUTENTICACIÓN (AuthController)
    // ==========================================

    @POST("auth/login")
    suspend fun login(@Body loginDto: LoginDto): Response<AuthResponse>

    @POST("auth/register")
    suspend fun registrar(@Body registroDto: RegistroDto): Response<Any>


    // ==========================================
    // 🎮 PRODUCTOS (ProductoController)
    // ==========================================

    // 1. LISTAR TODOS (GET)
    // Backend: @GetMapping (Lista completa)
    @GET("productos")
    suspend fun obtenerProductos(): Response<List<Producto>>

    // 2. OBTENER UNO SOLO (GET con ID)
    // Backend: @GetMapping("/{id}")
    @GET("productos/{id}")
    suspend fun obtenerProductoPorId(@Path("id") id: Long): Response<Producto>

    // 3. CREAR PRODUCTO (POST) - ¡Para tu panel Admin!
    // Backend: @PostMapping
    @POST("productos")
    suspend fun crearProducto(@Body producto: Producto): Response<Producto>

    // 4. ELIMINAR PRODUCTO (DELETE)
    // Backend: @DeleteMapping("/{id}")
    @DELETE("productos/{id}")
    suspend fun eliminarProducto(@Path("id") id: Long): Response<Void>

    // 5. ACTUALIZAR PRODUCTO (PUT) - Opcional, pero completa el CRUD
    // Backend: @PutMapping("/{id}")
    @PUT("productos/{id}")
    suspend fun actualizarProducto(@Path("id") id: Long, @Body producto: Producto): Response<Producto>
}