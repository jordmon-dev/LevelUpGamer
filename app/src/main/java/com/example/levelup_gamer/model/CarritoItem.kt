package com.example.levelup_gamer.model

// Importamos Producto para poder referenciarlo
import com.example.levelup_gamer.model.Producto

/**
 * Representa un artículo específico en el carrito de compras.
 * Combina un Producto con la cantidad deseada por el usuario.
 */
data class CarritoItem(
    val producto: Producto,
    val cantidad: Int,
)