package com.example.levelup_gamer.modelo

data class Producto(
    val id: Int,
    val codigo: String,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val stock: Int,
    val imagen: Int,
    val categoria: String,
    val destacado: Boolean = false
)