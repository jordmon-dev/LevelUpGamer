package com.example.levelup_gamer.modelo

data class CarritoResponse(
    val items: List<CarritoItem>,
    val total: Double,
    val cantidadTotal: Int
)