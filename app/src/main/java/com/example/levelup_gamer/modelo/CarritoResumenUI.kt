package com.example.levelup_gamer.modelo

data class CarritoResumenUI(
    val items: List<CarritoItemUI>,
    val subtotal: Double,
    val descuento: Double,
    val total: Double,
    val cantidadTotal: Int
)