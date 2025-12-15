package com.example.levelup_gamer.repository

import com.example.levelup_gamer.model.CarritoItem
import com.example.levelup_gamer.model.Producto

class CarritoRepository {

    // Lista en memoria (Simulación de carrito local)
    private val itemsEnCarrito = mutableListOf<CarritoItem>()

    fun obtenerCarrito(): List<CarritoItem> {
        return itemsEnCarrito.toList()
    }

    // ✅ AHORA RECIBE STRING EN LA IMAGEN
    fun agregarAlCarrito(id: Int, nombre: String, precio: Int, imagen: String) {
        val existente = itemsEnCarrito.find { it.producto.id.toInt() == id }

        if (existente != null) {
            existente.cantidad++
        } else {
            // Creamos un Producto temporal para el carrito
            val nuevoProducto = Producto(
                id = id.toInt(),
                nombre = nombre,
                precio = precio,
                imagen = imagen, // Guardamos la URL
                stock = 99,
                categoria = "Carrito",
                descripcion = "",
                codigo = ""
            )
            itemsEnCarrito.add(CarritoItem(nuevoProducto, 1))
        }
    }

    fun eliminarDelCarrito(id: Int) {
        val item = itemsEnCarrito.find { it.producto.id.toInt() == id }
        if (item != null) {
            if (item.cantidad > 1) {
                item.cantidad--
            } else {
                itemsEnCarrito.remove(item)
            }
        }
    }

    fun vaciarCarrito() {
        itemsEnCarrito.clear()
    }
}