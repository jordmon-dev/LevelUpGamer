package com.example.levelup_gamer.repository

import com.example.levelup_gamer.model.Producto
import com.example.levelup_gamer.remote.RetrofitInstance

class ProductoRepository {
    private val api = RetrofitInstance.api

    suspend fun obtenerProductos(): List<Producto> {
        return try {
            val response = api.obtenerProductos()
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}