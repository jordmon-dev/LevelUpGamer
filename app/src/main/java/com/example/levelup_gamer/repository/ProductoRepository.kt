package com.example.levelup_gamer.repository

import android.util.Log
import com.example.levelup_gamer.R
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
                    it.categoria.equals(categoria, ignoreCase = true)
                }
            }
        } catch (e: Exception) {
            Log.e("ProductoRepository", "Excepción: ${e.message}", e)
            getProductosMock().filter {
                it.categoria.equals(categoria, ignoreCase = true)
            }
        }
    }

    // Obtener productos destacados - MODIFICADO porque no hay campo "destacado"
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
                // Devuelve productos con precio > 40000 como "destacados"
                getProductosMock().filter { it.precio > 40000 }
            }
        } catch (e: Exception) {
            Log.e("ProductoRepository", "Excepción: ${e.message}", e)
            getProductosMock().filter { it.precio > 40000 }
        }
    }

    // Función para filtrar productos mock - CORREGIDO
    private suspend fun filtrarProductosMock(query: String): List<Producto> {
        return getProductosMock().filter {
            it.nombre.contains(query, ignoreCase = true) ||
                    it.descripcion.contains(query, ignoreCase = true) ||
                    it.categoria.contains(query, ignoreCase = true)
        }
    }

    // Datos mock para desarrollo/pruebas - ACTUALIZADO para coincidir con tu modelo
    private suspend fun getProductosMock(): List<Producto> {
        delay(500)

        return listOf(
            Producto(
                id = 1,
<<<<<<< HEAD
=======
                nombre = "God of War Ragnarok",
                precio = 49990.0,
                descripcion = "La secuela del aclamado God of War (2018)",
                stock = 15,
                categoria = "Acción",
                destacado = true,
>>>>>>> 26325cd399d3b00d6b44ae2d699d36192856a8d0
                codigo = "CQ001",
                nombre = "God of War Ragnarok",
                descripcion = "La secuela del aclamado God of War (2018)",
                precio = 49990.0,
                stock = 15,
                imagen = R.drawable.game_1,
                categoria = "Acción"
            ),
            Producto(
                id = 2,
<<<<<<< HEAD
=======
                nombre = "DualSense Controller",
                precio = 54990.0,
                descripcion = "Control inalámbrico para PS5",
                stock = 25,
                categoria = "Accesorios",
                destacado = false,
>>>>>>> 26325cd399d3b00d6b44ae2d699d36192856a8d0
                codigo = "AC001",
                nombre = "DualSense Controller",
                descripcion = "Control inalámbrico para PS5",
                precio = 54990.0,
                stock = 25,
                imagen = R.drawable.game_2,
                categoria = "Accesorios"
            ),
            Producto(
                id = 3,
<<<<<<< HEAD
=======
                nombre = "The Last of Us Part I",
                precio = 39990.0,
                descripcion = "Remake del clásico de Naughty Dog",
                stock = 10,
                categoria = "Aventura",
                destacado = true,
>>>>>>> 26325cd399d3b00d6b44ae2d699d36192856a8d0
                codigo = "AV001",
                nombre = "The Last of Us Part I",
                descripcion = "Remake del clásico de Naughty Dog",
                precio = 39990.0,
                stock = 10,
                imagen = R.drawable.game_3,
                categoria = "Aventura"
            ),
            Producto(
                id = 4,
<<<<<<< HEAD
=======
                nombre = "Xbox Series X",
                precio = 599990.0,
                descripcion = "Consola de nueva generación",
                stock = 8,
                categoria = "Consolas",
                destacado = true,
>>>>>>> 26325cd399d3b00d6b44ae2d699d36192856a8d0
                codigo = "CO001",
                nombre = "Xbox Series X",
                descripcion = "Consola de nueva generación",
                precio = 599990.0,
                stock = 8,
                imagen = R.drawable.game_4,
                categoria = "Consolas"
            ),
            Producto(
                id = 5,
<<<<<<< HEAD
=======
                nombre = "Nintendo Switch OLED",
                precio = 449990.0,
                descripcion = "Nintendo Switch con pantalla OLED",
                stock = 12,
                categoria = "Consolas",
                destacado = false,
>>>>>>> 26325cd399d3b00d6b44ae2d699d36192856a8d0
                codigo = "CO002",
                nombre = "Nintendo Switch OLED",
                descripcion = "Nintendo Switch con pantalla OLED",
                precio = 449990.0,
                stock = 12,
                imagen = R.drawable.game_5,
                categoria = "Consolas"
            ),
            Producto(
                id = 6,
<<<<<<< HEAD
=======
                nombre = "Elden Ring",
                precio = 44990.0,
                descripcion = "Juego de rol de acción de mundo abierto",
                stock = 18,
                categoria = "RPG",
                destacado = true,
>>>>>>> 26325cd399d3b00d6b44ae2d699d36192856a8d0
                codigo = "RP001",
                nombre = "Elden Ring",
                descripcion = "Juego de rol de acción de mundo abierto",
                precio = 44990.0,
                stock = 18,
                imagen = R.drawable.game_6,
                categoria = "RPG"
            ),
            Producto(
                id = 7,
                codigo = "ME001",
                nombre = "Polera Gamer Personalizada",
                descripcion = "Polera personalizable para gamers",
                precio = 14990.0,
                stock = 20,
                imagen = R.drawable.game_7,
                categoria = "Merchandise"
            )
        )
    }

    // Mock para buscar producto por ID
    private suspend fun getProductoMockPorId(id: Int): Producto? {
        delay(300)
        return getProductosMock().find { it.id == id }
    }

    // Función adicional para obtener productos filtrados - CORREGIDO
    suspend fun getProductosFiltrados(filtro: String): List<Producto> {
        delay(400)

        return when (filtro.lowercase()) {
            "destacados" -> getProductosMock().filter { it.precio > 40000 } // Sin campo destacado
            "consolas" -> getProductosMock().filter {
                it.categoria.equals("Consolas", ignoreCase = true)
            }
            "oferta" -> getProductosMock().filter { it.precio < 50000 }
            else -> getProductosMock()
        }
    }
}