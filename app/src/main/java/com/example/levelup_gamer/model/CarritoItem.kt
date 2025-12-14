package com.example.levelup_gamer.model

import com.example.levelup_gamer.model.Producto

data class CarritoItem(
    val producto: Producto,
    var cantidad: Int = 1
)