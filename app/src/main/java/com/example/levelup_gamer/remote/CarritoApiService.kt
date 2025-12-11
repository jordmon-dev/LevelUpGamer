// CarritoApiService.kt (en package com.example.levelup_gamer.remote)
package com.example.levelup_gamer.remote

import com.example.levelup_gamer.modelo.CarritoItemModel
import com.example.levelup_gamer.modelo.CarritoRequest
import com.example.levelup_gamer.modelo.CarritoResumenModel
import retrofit2.Response
import retrofit2.http.*

interface CarritoApiService {
    // GET /api/carrito?email=usuario@email.com
    @GET("carrito")
    suspend fun obtenerCarrito(@Query("email") email: String): Response<CarritoResumenModel>

    // POST /api/carrito/agregar
    @POST("carrito/agregar")
    suspend fun agregarAlCarrito(@Body request: CarritoRequest): Response<CarritoItemModel>

    // PUT /api/carrito/actualizar?id=1&cantidad=2&email=usuario@email.com
    @PUT("carrito/actualizar")
    suspend fun actualizarCantidad(
        @Query("id") id: Int,
        @Query("cantidad") cantidad: Int,
        @Query("email") email: String
    ): Response<CarritoItemModel>

    // DELETE /api/carrito/eliminar?id=1&email=usuario@email.com
    @DELETE("carrito/eliminar")
    suspend fun eliminarDelCarrito(
        @Query("id") id: Int,
        @Query("email") email: String
    ): Response<Void>
}