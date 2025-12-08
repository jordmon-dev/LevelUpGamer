package com.example.levelup_gamer.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Interface con los endpoints del backend.
 * Ajusta las rutas según la API que te dio el profe.
 */
interface ProductoApiService {

    @GET("productos")
    suspend fun getProductos(): Response<List<ProductoApi>>

    @GET("productos/{codigo}")
    suspend fun getProducto(
        @Path("codigo") codigo: String
    ): Response<ProductoApi>

    @POST("productos")
    suspend fun crearProducto(
        @Body producto: ProductoApi
    ): Response<ProductoApi>
}
