// Producto.kt
package com.example.levelup_gamer.modelo

data class Producto(
    val id: Int,
    val codigo: String,
    val nombre: String,
    val descripcion: String, // Cambiar de String? a String
    val precio: Double,
    val stock: Int,
    val imagen: Int,
    val categoria: String // Cambiar de String? a String
)