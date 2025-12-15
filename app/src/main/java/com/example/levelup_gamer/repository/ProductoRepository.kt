package com.example.levelup_gamer.repository

import android.util.Log
import com.example.levelup_gamer.model.DatosPrueba
import com.example.levelup_gamer.model.Producto
import com.example.levelup_gamer.remote.RetrofitInstance

class ProductoRepository {

    // Instancia de la API
    private val api = RetrofitInstance.api

    // 1. Obtener todos los productos (Híbrido: Internet + Offline)
    suspend fun obtenerProductos(): List<Producto> {
        return try {
            // ✅ CORRECCIÓN: Usamos 'obtenerProductos()' que es como se llama en tu API Service
            val response = api.obtenerProductos()

            if (response.isSuccessful && response.body() != null) {
                // Si hay internet y datos, usamos los del servidor
                response.body()!!
            } else {
                // Si el servidor falla, usamos el respaldo local
                Log.e("REPO", "Error del servidor: ${response.code()}. Usando datos locales.")
                DatosPrueba.listaProductos
            }
        } catch (e: Exception) {
            // Si no hay internet (Modo Avión), usamos el respaldo local
            Log.e("REPO", "Sin conexión: ${e.message}. Usando datos locales.")
            DatosPrueba.listaProductos
        }
    }

    // 2. Crear un producto
    suspend fun crearProducto(producto: Producto): Boolean {
        return try {
            val response = api.crearProducto(producto)
            response.isSuccessful
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