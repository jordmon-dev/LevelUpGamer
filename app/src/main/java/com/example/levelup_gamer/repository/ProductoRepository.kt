package com.example.levelup_gamer.repository

import android.util.Log
import com.example.levelup_gamer.modelo.Producto
import com.example.levelup_gamer.remote.ProductoRetrofitInstance
import kotlinx.coroutines.delay

class ProductoRepository {

    private val apiService = ProductoRetrofitInstance.api

    // Obtener todos los productos
    suspend fun getProductos(): List<Producto> {
        return try {
            Log.d("ProductoRepository", "Obteniendo lista de productos")
            val response = apiService.getProductos()

            if (response.isSuccessful) {
                response.body()?.let { productos ->
                    Log.d("ProductoRepository", "Productos obtenidos: ${productos.size} items")
                    productos
                } ?: emptyList()
            } else {
                Log.e("ProductoRepository", "Error: ${response.code()} - ${response.message()}")
                // Retornar lista vacía o mock en caso de error
                getProductosMock()
            }
        } catch (e: Exception) {
            Log.e("ProductoRepository", "Excepción: ${e.message}")
            // En caso de error de red, retornar datos mock
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
                // Intentar con mock
                getProductoMockPorId(id)
            }
        } catch (e: Exception) {
            Log.e("ProductoRepository", "Excepción: ${e.message}")
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
                // Filtrar mock por query
                getProductosMock().filter {
                    it.nombre.contains(query, ignoreCase = true) ||
                            it.descripcion.contains(query, ignoreCase = true) ||
                            it.categoria.contains(query, ignoreCase = true)
                }
            }
        } catch (e: Exception) {
            Log.e("ProductoRepository", "Excepción: ${e.message}")
            // Filtrar mock por query
            getProductosMock().filter {
                it.nombre.contains(query, ignoreCase = true) ||
                        it.descripcion.contains(query, ignoreCase = true) ||
                        it.categoria.contains(query, ignoreCase = true)
            }
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
                // Filtrar mock por categoría
                getProductosMock().filter {
                    it.categoria.equals(categoria, ignoreCase = true)
                }
            }
        } catch (e: Exception) {
            Log.e("ProductoRepository", "Excepción: ${e.message}")
            // Filtrar mock por categoría
            getProductosMock().filter {
                it.categoria.equals(categoria, ignoreCase = true)
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
                // Filtrar mock por destacado
                getProductosMock().filter { it.destacado }
            }
        } catch (e: Exception) {
            Log.e("ProductoRepository", "Excepción: ${e.message}")
            // Filtrar mock por destacado
            getProductosMock().filter { it.destacado }
        }
    }

    // Datos mock para desarrollo/pruebas (similar a tu CarritoRepository)
    private suspend fun getProductosMock(): List<Producto> {
        delay(500) // Simular delay de red

        return listOf(
            Producto(
                id = 1,
                codigo = "CQ001",
                nombre = "God of War Ragnarok",
                descripcion = "La secuela del aclamado God of War (2018). Kratos y Atreus deben viajar a cada uno de los Nueve Reinos en busca de respuestas.",
                precio = 49990.0,
                stock = 15,
                imagen = "god_of_war",
                imagenUrl = "https://ejemplo.com/gow.jpg",
                categoria = "Acción"
            ),
            Producto(
                id = 2,
                codigo = "AC001",
                nombre = "DualSense Controller",
                descripcion = "Control inalámbrico para PS5 con retroalimentación háptica y gatillos adaptativos.",
                precio = 54990.0,
                stock = 25,
                imagen = "dualsense",
                imagenUrl = "https://ejemplo.com/dualsense.jpg",
                categoria = "Accesorios"
            ),
            Producto(
                id = 3,
                codigo = "AV001",
                nombre = "The Last of Us Part I",
                descripcion = "Remake del clásico de Naughty Dog con gráficos y jugabilidad mejorados.",
                precio = 39990.0,
                stock = 10,
                imagen = "tlou",
                imagenUrl = "https://ejemplo.com/tlou.jpg",
                categoria = "Aventura",
                destacado = true
            ),
            Producto(
                id = 4,
                codigo = "CO001",
                nombre = "Xbox Series X",
                descripcion = "Consola de nueva generación con 4K nativo y hasta 120 FPS.",
                precio = 599990.0,
                stock = 8,
                imagen = "xbox",
                imagenUrl = "https://ejemplo.com/xbox.jpg",
                categoria = "Consolas",
                destacado = true
            ),
            Producto(
                id = 5,
                codigo = "CO002",
                nombre = "Nintendo Switch OLED",
                descripcion = "Nintendo Switch con pantalla OLED de 7 pulgadas y mejoras en el audio.",
                precio = 449990.0,
                stock = 12,
                imagen = "switch",
                imagenUrl = "https://ejemplo.com/switch.jpg",
                categoria = "Consolas"
            ),
            Producto(
                id = 6,
                codigo = "RP001",
                nombre = "Elden Ring",
                descripcion = "Juego de rol de acción de mundo abierto desarrollado por FromSoftware.",
                precio = 44990.0,
                stock = 18,
                imagen = "elden_ring",
                imagenUrl = "https://ejemplo.com/elden.jpg",
                categoria = "RPG",
                destacado = true
            )
        )
    }

    // Mock para buscar producto por ID
    private suspend fun getProductoMockPorId(id: Int): Producto? {
        delay(300)
        return getProductosMock().find { it.id == id }
    }

    // Función adicional para obtener productos filtrados (similar al mock de carrito)
    suspend fun getProductosFiltrados(filtro: String): List<Producto> {
        delay(400)

        return when (filtro.toLowerCase()) {
            "destacados" -> getProductosMock().filter { it.destacado }
            "consolas" -> getProductosMock().filter { it.categoria.equals("Consolas", ignoreCase = true) }
            "oferta" -> getProductosMock().filter { it.precio < 50000 }
            else -> getProductosMock()
        }
    }
}