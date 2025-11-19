package com.example.levelup_gamer.model

data class Producto(
    val codigo: String,
    val nombre: String,
    val precio: Double,
    val categoria: String,
    val descripcion: String = "",
    val imagen:Int ?=null
)

