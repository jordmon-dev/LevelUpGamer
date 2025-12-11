// ProductoApiService.kt
package com.example.levelup_gamer.remote

import com.example.levelup_gamer.modelo.Producto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductoApiService {
    @GET("productos")
    suspend fun getProductos(): Response<List<Producto>>

    @GET("productos/{id}")
    suspend fun getProductoPorId(@Path("id") id: Int): Response<Producto>

    @GET("productos/buscar")
    suspend fun buscarProductos(@Query("q") query: String): Response<List<Producto>>

    @GET("productos/categoria/{categoria}")
    suspend fun getProductosPorCategoria(@Path("categoria") categoria: String): Response<List<Producto>>

    @GET("productos/destacados")
    suspend fun getProductosDestacados(): Response<List<Producto>>
}