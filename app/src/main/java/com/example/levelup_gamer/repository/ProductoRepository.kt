package com.example.levelup_gamer.repository

import android.util.Log
import com.example.levelup_gamer.model.Producto
import com.example.levelup_gamer.remote.RetrofitInstance
import retrofit2.Response

class ProductoRepository {

    // Instancia de la API
    private val api = RetrofitInstance.api

    // 1. Obtener todos los productos
    suspend fun obtenerProductos(): List<Producto> {
        return try {
            val response = api.obtenerProductos()
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                Log.e("REPO", "Error: ${response.code()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("REPO", "Error de red: ${e.message}")
            emptyList()
        }
    }

    // 2. Crear un producto (Para el Admin)
    suspend fun crearProducto(producto: Producto): Boolean {
        return try {
            val response = api.crearProducto(producto)
            response.isSuccessful // Devuelve true si se creó (Código 201)
        } catch (e: Exception) {
            Log.e("REPO", "Error al crear: ${e.message}")
            false
        }
    }

    // 3. Eliminar producto
    suspend fun eliminarProducto(id: Long): Boolean {
        return try {
            val response = api.eliminarProducto(id)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }
}