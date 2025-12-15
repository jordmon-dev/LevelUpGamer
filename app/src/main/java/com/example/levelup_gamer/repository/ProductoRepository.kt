package com.example.levelup_gamer.repository

import android.util.Log
import com.example.levelup_gamer.model.DatosPrueba
import com.example.levelup_gamer.model.Producto
import com.example.levelup_gamer.remote.RetrofitInstance

class ProductoRepository {

    private val api = RetrofitInstance.api

    // Lógica Híbrida: Intenta Internet -> Si falla -> Usa Local
    suspend fun obtenerProductos(): List<Producto> {
        return try {
            // 1. Intentamos conectar a tu Backend Spring Boot
            val response = api.obtenerProductos()

            if (response.isSuccessful && response.body() != null && response.body()!!.isNotEmpty()) {
                Log.d("REPO", "✅ Productos cargados desde BACKEND")
                response.body()!!
            } else {
                Log.w("REPO", "⚠️ Backend vacío o error. Usando Datos Locales.")
                DatosPrueba.listaProductos
            }
        } catch (e: Exception) {
            // 2. Si no hay conexión (Modo Avión o Server apagado), usamos local
            Log.e("REPO", "❌ Sin conexión (${e.message}). Usando Datos Locales.")
            DatosPrueba.listaProductos
        }
    }

    // El resto de funciones se mantienen igual...
    suspend fun crearProducto(producto: Producto): Boolean {
        return try {
            val response = api.crearProducto(producto)
            response.isSuccessful
        } catch (e: Exception) { false }
    }

    suspend fun eliminarProducto(id: Long): Boolean {
        return try {
            val response = api.eliminarProducto(id)
            response.isSuccessful
        } catch (e: Exception) { false }
    }
}