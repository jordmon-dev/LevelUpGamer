// CarritoRepository.kt (en package com.example.levelup_gamer.repository)
package com.example.levelup_gamer.repository

import com.example.levelup_gamer.modelo.CarritoItemModel
import com.example.levelup_gamer.modelo.CarritoRequest
import com.example.levelup_gamer.modelo.CarritoResumenModel
import com.example.levelup_gamer.remote.CarritoApiService
import com.example.levelup_gamer.remote.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CarritoRepository {
    private val apiService: CarritoApiService = RetrofitInstance.carritoApiService

    suspend fun obtenerCarrito(email: String): Result<CarritoResumenModel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.obtenerCarrito(email)
                if (response.isSuccessful) {
                    Result.success(response.body() ?: crearCarritoVacio())
                } else {
                    Result.failure(Exception("Error: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun agregarAlCarrito(request: CarritoRequest): Result<CarritoItemModel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.agregarAlCarrito(request)
                if (response.isSuccessful) {
                    Result.success(response.body() ?: throw Exception("Sin datos"))
                } else {
                    Result.failure(Exception("Error: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun actualizarCantidad(id: Int, cantidad: Int, email: String): Result<CarritoItemModel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.actualizarCantidad(id, cantidad, email)
                if (response.isSuccessful) {
                    Result.success(response.body() ?: throw Exception("Sin datos"))
                } else {
                    Result.failure(Exception("Error: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun eliminarDelCarrito(id: Int, email: String): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.eliminarDelCarrito(id, email)
                if (response.isSuccessful) {
                    Result.success(true)
                } else {
                    Result.failure(Exception("Error: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    private fun crearCarritoVacio(): CarritoResumenModel {
        return CarritoResumenModel(
            items = emptyList(),
            subtotal = 0.0,
            descuento = 0.0,
            total = 0.0,
            cantidadTotal = 0
        )
    }
}