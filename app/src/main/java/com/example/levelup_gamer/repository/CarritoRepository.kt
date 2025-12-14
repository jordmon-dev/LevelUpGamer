package com.example.levelup_gamer.repository

import com.example.levelup_gamer.model.CarritoItem
import com.example.levelup_gamer.model.Producto

class CarritoRepository {

    companion object {
        // Lista en memoria (se borra al cerrar la app)
        private val carritoEnMemoria = mutableListOf<CarritoItem>()
    }

    fun obtenerCarrito(): List<CarritoItem> {
        return carritoEnMemoria.toList()
    }

    fun agregarAlCarrito(id: Int, nombre: String, precio: Int, imagen: Int) {
        // Buscamos si el producto ya existe en el carrito por ID
        val itemExistente = carritoEnMemoria.find { it.producto.id == id }

        if (itemExistente != null) {
            // Si existe, solo aumentamos la cantidad
            itemExistente.cantidad += 1
        } else {
            // CORRECCIÓN: Si no existe, creamos el Producto primero
            // (Usamos datos temporales para los campos que no tenemos aquí, como descripción)
            val nuevoProducto = Producto(
                id = id,
                nombre = nombre,
                precio = precio.toDouble(), // Asegúrate que Producto use Double
                imagen = imagen,
                descripcion = "", // Valor por defecto
                stock = 99,       // Valor por defecto
                codigo = "",      // Valor por defecto
                categoria = ""    // Valor por defecto
            )

            // Ahora sí creamos el CarritoItem válido
            carritoEnMemoria.add(CarritoItem(producto = nuevoProducto, cantidad = 1))
        }
    }

    fun eliminarDelCarrito(id: Int) {
        // Buscamos por el ID del producto interno
        carritoEnMemoria.removeAll { it.producto.id == id }
    }

    fun vaciarCarrito() {
        carritoEnMemoria.clear()
    }
}