package com.example.levelup_gamer.repository

import android.util.Log
import com.example.levelup_gamer.modelo.CarritoItemModel
import com.example.levelup_gamer.modelo.CarritoRequest
import com.example.levelup_gamer.modelo.CarritoResumenModel
import com.example.levelup_gamer.modelo.CarritoResumenUI
import com.example.levelup_gamer.modelo.CarritoItemUI
import com.example.levelup_gamer.modelo.toCarritoResumenUI
import com.example.levelup_gamer.remote.CarritoRetrofitInstance
import kotlinx.coroutines.delay

class CarritoRepository {

    private val apiService = CarritoRetrofitInstance.api

    suspend fun getCarritoUsuario(email: String): CarritoResumenUI? {
        return try {
            Log.d("CarritoRepository", "Obteniendo carrito para: $email")
            val response = apiService.getCarritoUsuario(email)

            if (response.isSuccessful) {
                response.body()?.let { carritoModel ->
                    val carritoUI = carritoModel.toCarritoResumenUI()
                    Log.d("CarritoRepository", "Carrito obtenido: ${carritoUI.items.size} items")
                    carritoUI
                }
            } else {
                Log.e("CarritoRepository", "Error: ${response.code()} - ${response.message()}")
                // Retornar carrito vacío
                CarritoResumenUI(
                    items = emptyList(),
                    subtotal = 0.0,
                    descuento = 0.0,
                    total = 0.0,
                    cantidadTotal = 0
                )
            }
        } catch (e: Exception) {
            Log.e("CarritoRepository", "Excepción: ${e.message}")
            CarritoResumenUI(
                items = emptyList(),
                subtotal = 0.0,
                descuento = 0.0,
                total = 0.0,
                cantidadTotal = 0
            )
        }
    }

    suspend fun agregarAlCarrito(request: CarritoRequest): Boolean {
        return try {
            Log.d("CarritoRepository", "Agregando al carrito: ${request.productoId}")
            val response = apiService.agregarAlCarrito(request)

            if (response.isSuccessful) {
                Log.d("CarritoRepository", "Producto agregado exitosamente")
                true
            } else {
                Log.e("CarritoRepository", "Error al agregar: ${response.code()}")
                false
            }
        } catch (e: Exception) {
            Log.e("CarritoRepository", "Excepción: ${e.message}")
            false
        }
    }

    suspend fun actualizarCantidad(itemId: Int, cantidad: Int): Boolean {
        return try {
            Log.d("CarritoRepository", "Actualizando cantidad item $itemId a $cantidad")
            val response = apiService.actualizarCantidad(itemId, cantidad)

            if (response.isSuccessful) {
                Log.d("CarritoRepository", "Cantidad actualizada exitosamente")
                true
            } else {
                Log.e("CarritoRepository", "Error al actualizar: ${response.code()}")
                false
            }
        } catch (e: Exception) {
            Log.e("CarritoRepository", "Excepción: ${e.message}")
            false
        }
    }

    suspend fun eliminarDelCarrito(itemId: Int): Boolean {
        return try {
            Log.d("CarritoRepository", "Eliminando item $itemId")
            val response = apiService.eliminarDelCarrito(itemId)

            if (response.isSuccessful) {
                Log.d("CarritoRepository", "Item eliminado exitosamente")
                true
            } else {
                Log.e("CarritoRepository", "Error al eliminar: ${response.code()}")
                false
            }
        } catch (e: Exception) {
            Log.e("CarritoRepository", "Excepción: ${e.message}")
            false
        }
    }

    suspend fun vaciarCarrito(email: String): Boolean {
        return try {
            Log.d("CarritoRepository", "Vaciando carrito de: $email")
            val response = apiService.vaciarCarrito(email)

            if (response.isSuccessful) {
                Log.d("CarritoRepository", "Carrito vaciado exitosamente")
                true
            } else {
                Log.e("CarritoRepository", "Error al vaciar: ${response.code()}")
                false
            }
        } catch (e: Exception) {
            Log.e("CarritoRepository", "Excepción: ${e.message}")
            false
        }
    }

    suspend fun getResumen(email: String): CarritoResumenUI? {
        return try {
            val response = apiService.getResumenCarrito(email)

            if (response.isSuccessful) {
                response.body()?.toCarritoResumenUI()
            } else {
                CarritoResumenUI(
                    items = emptyList(),
                    subtotal = 0.0,
                    descuento = 0.0,
                    total = 0.0,
                    cantidadTotal = 0
                )
            }
        } catch (e: Exception) {
            CarritoResumenUI(
                items = emptyList(),
                subtotal = 0.0,
                descuento = 0.0,
                total = 0.0,
                cantidadTotal = 0
            )
        }
    }

    // Función mock actualizada
    suspend fun getCarritoMock(email: String): CarritoResumenUI {
        delay(500)

        return if (email.endsWith("@duocuc.cl")) {
            CarritoResumenUI(
                items = listOf(
                    CarritoItemUI(
                        id = 1,
                        productoId = 101,
                        productoNombre = "God of War Ragnarok",
                        productoPrecio = 49990.0,
                        productoStock = 10,
                        cantidad = 1,
                        usuarioEmail = email
                    ),
                    CarritoItemUI(
                        id = 2,
                        productoId = 102,
                        productoNombre = "DualSense Controller",
                        productoPrecio = 54990.0,
                        productoStock = 20,
                        cantidad = 2,
                        usuarioEmail = email
                    )
                ),
                subtotal = 159980.0,
                descuento = 31996.0,
                total = 127984.0,
                cantidadTotal = 3
            )
        } else {
            CarritoResumenUI(
                items = listOf(
                    CarritoItemUI(
                        id = 1,
                        productoId = 101,
                        productoNombre = "The Last of Us Part I",
                        productoPrecio = 39990.0,
                        productoStock = 15,
                        cantidad = 1,
                        usuarioEmail = email
                    )
                ),
                subtotal = 39990.0,
                descuento = 3999.0,
                total = 35991.0,
                cantidadTotal = 1
            )
        }
    }
}