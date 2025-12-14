package com.example.levelup_gamer.model

// Importamos Producto para poder referenciarlo
import com.example.levelup_gamer.model.Producto

/**
 * Representa un artículo específico en el carrito de compras.
 * Combina un Producto con la cantidad deseada por el usuario.
 */
// En tu modelo o en el mismo ViewModel
data class CarritoItem(
    val producto: Producto,
    val cantidad: Int = 1
)

data class ResumenCarrito(
    val items: List<CarritoItem> = emptyList(),
    val subtotal: Double = 0.0,
    val descuento: Double = 0.0,
    val total: Double = 0.0
)