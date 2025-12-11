package com.example.levelup_gamer.repository

import android.util.Log
import com.example.levelup_gamer.modelo.Producto
import com.example.levelup_gamer.remote.RetrofitInstance
import kotlinx.coroutines.delay

class ProductoRepository {

    private val apiService = RetrofitInstance.productoApiService

    // Obtener todos los productos
    suspend fun getProductos(): List<Producto> {
        return try {
            Log.d("ProductoRepository", "Obteniendo productos...")
            val response = apiService.getProductos()

            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                Log.e("ProductoRepository", "Error: ${response.code()}")
                getProductosMock()
            }
        } catch (e: Exception) {
            Log.e("ProductoRepository", "Error: ${e.message}")
            getProductosMock()
        }
    }

    // Obtener producto por ID
    suspend fun getProductoPorId(id: Int): Producto? {
        return try {
            Log.d("ProductoRepository", "Obteniendo producto ID: $id")
            val response = apiService.getProductoPorId(id)

            if (response.isSuccessful) {
                response.body()?.let { producto ->
                    Log.d("ProductoRepository", "Producto encontrado: ${producto.nombre}")
                    producto
                }
            } else {
                Log.e("ProductoRepository", "Producto no encontrado: ${response.code()}")
                getProductoMockPorId(id)
            }
        } catch (e: Exception) {
            Log.e("ProductoRepository", "Excepción: ${e.message}", e)
            getProductoMockPorId(id)
        }
    }

    // Buscar productos por nombre o categoría
    suspend fun buscarProductos(query: String): List<Producto> {
        return try {
            Log.d("ProductoRepository", "Buscando productos: $query")
            val response = apiService.buscarProductos(query)

            if (response.isSuccessful) {
                response.body()?.let { productos ->
                    Log.d("ProductoRepository", "Resultados: ${productos.size} items")
                    productos
                } ?: emptyList()
            } else {
                Log.e("ProductoRepository", "Error en búsqueda: ${response.code()}")
                filtrarProductosMock(query)
            }
        } catch (e: Exception) {
            Log.e("ProductoRepository", "Excepción: ${e.message}", e)
            filtrarProductosMock(query)
        }
    }

    // Obtener productos por categoría
    suspend fun getProductosPorCategoria(categoria: String): List<Producto> {
        return try {
            Log.d("ProductoRepository", "Obteniendo productos categoría: $categoria")
            val response = apiService.getProductosPorCategoria(categoria)

            if (response.isSuccessful) {
                response.body()?.let { productos ->
                    Log.d("ProductoRepository", "Categoría $categoria: ${productos.size} items")
                    productos
                } ?: emptyList()
            } else {
                Log.e("ProductoRepository", "Error categoría: ${response.code()}")
                getProductosMock().filter {
                    it.categoria?.equals(categoria, ignoreCase = true) == true
                }
            }
        } catch (e: Exception) {
            Log.e("ProductoRepository", "Excepción: ${e.message}", e)
            getProductosMock().filter {
                it.categoria?.equals(categoria, ignoreCase = true) == true
            }
        }
    }

    // Obtener productos destacados
    suspend fun getProductosDestacados(): List<Producto> {
        return try {
            Log.d("ProductoRepository", "Obteniendo productos destacados")
            val response = apiService.getProductosDestacados()

            if (response.isSuccessful) {
                response.body()?.let { productos ->
                    Log.d("ProductoRepository", "Destacados: ${productos.size} items")
                    productos
                } ?: emptyList()
            } else {
                Log.e("ProductoRepository", "Error destacados: ${response.code()}")
                getProductosMock().filter { it.destacado }
            }
        } catch (e: Exception) {
            Log.e("ProductoRepository", "Excepción: ${e.message}", e)
            getProductosMock().filter { it.destacado }
        }
    }

    // Función para filtrar productos mock
    private suspend fun filtrarProductosMock(query: String): List<Producto> {
        return getProductosMock().filter {
            it.nombre.contains(query, ignoreCase = true) ||
                    it.descripcion?.contains(query, ignoreCase = true) == true ||
                    it.categoria?.contains(query, ignoreCase = true) == true
        }
    }

    // Datos mock para desarrollo/pruebas
    private suspend fun getProductosMock(): List<Producto> {
        delay(500) // Simular delay de red

        return listOf(
            Producto(
                id = 1,
                nombre = "God of War Ragnarok",
                precio = 49990.0,
                descripcion = "La secuela del aclamado God of War (2018)",
                stock = 15,
                imagen = "god_of_war",
                categoria = "Acción",
                destacado = true,
                codigo = "CQ001",
                imagenUrl = "https://ejemplo.com/gow.jpg"
            ),
            Producto(
                id = 2,
                nombre = "DualSense Controller",
                precio = 54990.0,
                descripcion = "Control inalámbrico para PS5",
                stock = 25,
                imagen = "dualsense",
                categoria = "Accesorios",
                destacado = false,
                codigo = "AC001",
                imagenUrl = "https://ejemplo.com/dualsense.jpg"
            ),
            Producto(
                id = 3,
                nombre = "The Last of Us Part I",
                precio = 39990.0,
                descripcion = "Remake del clásico de Naughty Dog",
                stock = 10,
                imagen = "tlou",
                categoria = "Aventura",
                destacado = true,
                codigo = "AV001",
                imagenUrl = "https://ejemplo.com/tlou.jpg"
            ),
            Producto(
                id = 4,
                nombre = "Xbox Series X",
                precio = 599990.0,
                descripcion = "Consola de nueva generación",
                stock = 8,
                imagen = "xbox",
                categoria = "Consolas",
                destacado = true,
                codigo = "CO001",
                imagenUrl = "https://ejemplo.com/xbox.jpg"
            ),
            Producto(
                id = 5,
                nombre = "Nintendo Switch OLED",
                precio = 449990.0,
                descripcion = "Nintendo Switch con pantalla OLED",
                stock = 12,
                imagen = "switch",
                categoria = "Consolas",
                destacado = false,
                codigo = "CO002",
                imagenUrl = "https://ejemplo.com/switch.jpg"
            ),
            Producto(
                id = 6,
                nombre = "Elden Ring",
                precio = 44990.0,
                descripcion = "Juego de rol de acción de mundo abierto",
                stock = 18,
                imagen = "elden_ring",
                categoria = "RPG",
                destacado = true,
                codigo = "RP001",
                imagenUrl = "https://ejemplo.com/elden.jpg"
            )
        )
    }

    // Mock para buscar producto por ID
    private suspend fun getProductoMockPorId(id: Int): Producto? {
        delay(300)
        return getProductosMock().find { it.id == id }
    }

    // Función adicional para obtener productos filtrados
    suspend fun getProductosFiltrados(filtro: String): List<Producto> {
        delay(400)

        return when (filtro.lowercase()) {
            "destacados" -> getProductosMock().filter { it.destacado }
            "consolas" -> getProductosMock().filter {
                it.categoria?.equals("Consolas", ignoreCase = true) == true
            }
            "oferta" -> getProductosMock().filter { it.precio < 50000 }
            else -> getProductosMock()
        }
    }
}