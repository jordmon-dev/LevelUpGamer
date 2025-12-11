package com.example.levelup_gamer.modelo

data class CarritoResponse(
    val items: List<CarritoItemUI>,
    val total: Double,
    val cantidadTotal: Int
)