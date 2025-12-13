// Producto.kt
package com.example.levelup_gamer.modelo

data class Producto(
    val id: Int,
    val codigo: String,
    val nombre: String,
    val descripcion: String, // Cambiar de String? a String
    val precio: Double,
<<<<<<< HEAD
    val stock: Int,
    val imagen: Int,
    val categoria: String // Cambiar de String? a String
=======
    val descripcion: String? = null,
    val imagen: Int? = null, // <--- CORREGIDO: De String? a Int?
    val stock: Int,
    val categoria: String? = null,
    val destacado: Boolean = false,
    val codigo: String? = null,
    val imagenUrl: String? = null
>>>>>>> 26325cd399d3b00d6b44ae2d699d36192856a8d0
)