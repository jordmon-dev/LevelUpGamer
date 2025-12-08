package com.example.levelup_gamer.remote

import com.example.levelup_gamer.model.Producto

/**
 * Modelo que representa al producto tal como viene del backend.
 * Aquí NO usamos la imagen porque los drawables son locales.
 */
data class ProductoApi(
    val codigo: String,
    val nombre: String,
    val precio: Double,
    val categoria: String,
    val descripcion: String
) {
    fun aProductoLocal(): Producto =
        Producto(
            codigo = codigo,
            nombre = nombre,
            precio = precio,
            categoria = categoria,
            descripcion = descripcion,
            imagen = null // La imagen se maneja localmente (R.drawable.game_X)
        )
}
