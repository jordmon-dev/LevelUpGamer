package com.example.levelup_gamer.remote

import com.example.levelup_gamer.modelo.CarritoItemModel
import com.example.levelup_gamer.modelo.CarritoRequest
import com.example.levelup_gamer.modelo.CarritoResumenModel
import retrofit2.Response
import retrofit2.http.*

interface CarritoApiService {

    @GET("carrito/{email}")
    suspend fun getCarritoUsuario(
        @Path("email") email: String
    ): Response<CarritoResumenModel>  // ← Devuelve CarritoResumenModel

    @POST("carrito/agregar")
    suspend fun agregarAlCarrito(
        @Body request: CarritoRequest
    ): Response<CarritoItemModel>  // ← Devuelve CarritoItemModel

    @PUT("carrito/actualizar/{id}")
    suspend fun actualizarCantidad(
        @Path("id") itemId: Int,
        @Body cantidad: Int
    ): Response<CarritoItemModel>  // ← Devuelve CarritoItemModel

    @DELETE("carrito/eliminar/{id}")
    suspend fun eliminarDelCarrito(
        @Path("id") itemId: Int
    ): Response<Unit>

    @DELETE("carrito/vaciar/{email}")
    suspend fun vaciarCarrito(
        @Path("email") email: String
    ): Response<Unit>

    @GET("carrito/resumen/{email}")
    suspend fun getResumenCarrito(
        @Path("email") email: String
    ): Response<CarritoResumenModel>  // ← Devuelve CarritoResumenModel
}