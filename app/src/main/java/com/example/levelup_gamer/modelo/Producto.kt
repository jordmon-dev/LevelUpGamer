// Producto.kt
package com.example.levelup_gamer.modelo

data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val descripcion: String? = null,
    val imagen: Int? = null, // <--- CORREGIDO: De String? a Int?
    val stock: Int,
    val categoria: String? = null,
    val destacado: Boolean = false,
    val codigo: String? = null,
    val imagenUrl: String? = null
)