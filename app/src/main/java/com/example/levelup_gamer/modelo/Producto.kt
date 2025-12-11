// Producto.kt
package com.example.levelup_gamer.modelo

data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val descripcion: String? = null,
    val imagen: String? = null,
    val stock: Int,
    val categoria: String? = null,
    val destacado: Boolean = false,           // Agregar si no está
    val codigo: String? = null,               // Agregar si no está
    val imagenUrl: String? = null             // Agregar si no está
)