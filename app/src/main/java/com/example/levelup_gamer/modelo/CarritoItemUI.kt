package com.example.levelup_gamer.modelo

data class CarritoItemUI(
    val id: Int? = null,
    val productoId: Int,
    val productoNombre: String,  // ⬅️ AQUÍ está el problema
    val productoPrecio: Double,  // ⬅️ Y AQUÍ
    val cantidad: Int,
    val usuarioEmail: String
) {
    // Propiedades computadas para compatibilidad
    val nombre: String get() = productoNombre  // ⬅️ Esto permite usar item.nombre
    val precio: Double get() = productoPrecio  // ⬅️ Esto permite usar item.precio
}