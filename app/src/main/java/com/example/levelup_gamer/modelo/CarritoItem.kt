// Este archivo se ha vaciado para evitar la redeclaración de clases.
// Todo el código relacionado con el carrito se ha consolidado en CarritoItemUI.kt
package com.example.levelup_gamer.modelo


// Modelo para items individuales del carrito desde la API
data class CarritoItemModel(
    val id: Int? = null,
    val productoId: Int,
    val productoNombre: String,
    val productoPrecio: Double,
    val productoDescripcion: String? = null,
    val productoImagen: String? = null,
    val productoStock: Int,
    val cantidad: Int,
    val usuarioEmail: String,
    val fechaAgregado: String? = null
)

// Modelo para el resumen completo desde la API
data class CarritoResumenModel(
    val items: List<CarritoItemModel>,
    val subtotal: Double,
    val descuento: Double,
    val total: Double,
    val cantidadTotal: Int
)

// Request para agregar/actualizar items en la API

