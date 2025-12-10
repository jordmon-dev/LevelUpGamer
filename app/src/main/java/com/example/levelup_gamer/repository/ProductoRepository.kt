package com.example.levelup_gamer.repository

import com.example.levelup_gamer.model.Producto
import com.example.levelup_gamer.remote.ProductoApi
import com.example.levelup_gamer.remote.ProductoApiService
import com.example.levelup_gamer.remote.RetrofitClient

/**
 * Repositorio encargado de hablar con la API de productos
 * y devolver modelos locales (Producto).
 */
class ProductoRepository(
    private val api: ProductoApiService = RetrofitClient.productoApi
) {

    suspend fun obtenerProductos(): List<Producto> {
        val response = api.getProductos()
        if (response.isSuccessful) {
            val listaApi: List<ProductoApi> = response.body() ?: emptyList()
            return listaApi.map { it.aProductoLocal() }
        } else {
            throw Exception("Error ${response.code()} al obtener productos")
        }
    }

    suspend fun obtenerProductoPorCodigo(codigo: String): Producto? {
        val response = api.getProducto(codigo)
        return if (response.isSuccessful) {
            response.body()?.aProductoLocal()
        } else {
            null
        }
    }
}
