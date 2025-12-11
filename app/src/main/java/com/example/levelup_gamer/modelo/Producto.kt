package com.example.levelup_gamer.modelo

data class Producto(
    val id: Int,
    val codigo: String,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val stock: Int,
    val imagen: String,
    val imagenUrl: String? = null,
    val categoria: String,
    val destacado: Boolean = false,  // Agregar esta propiedad
    val plataforma: String? = null,  // Opcional
    val rating: Double? = null       // Opcional
)