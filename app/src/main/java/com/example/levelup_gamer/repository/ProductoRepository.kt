package com.example.levelup_gamer.repository

import android.util.Log
import com.example.levelup_gamer.model.DatosPrueba
import com.example.levelup_gamer.model.Producto
import com.example.levelup_gamer.remote.RetrofitInstance
import com.example.levelup_gamer.remote.UsuarioInstance // ✅ IMPORTANTE: Necesario para el token

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

    // ✅ FUNCIÓN CORREGIDA: Ahora envía el Token
    suspend fun crearProducto(producto: Producto): Boolean {
        return try {
            // 1. Obtenemos el token guardado
            val token = UsuarioInstance.getBearerToken()

            // Si no hay token (no es admin o no se logueó), fallamos
            if (token == null) return false

            // 2. Llamamos a la API enviando (Token, Producto)
            val response = api.crearProducto(token, producto)
            response.isSuccessful
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // ✅ FUNCIÓN CORREGIDA: Ahora envía el Token
    suspend fun eliminarProducto(id: Long): Boolean {
        return try {
            // 1. Obtenemos el token guardado
            val token = UsuarioInstance.getBearerToken()

            if (token == null) return false

            // 2. Llamamos a la API enviando (Token, ID)
            val response = api.eliminarProducto(token, id)
            response.isSuccessful
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}