package com.example.levelup_gamer.model

// Definimos la estructura de los datos aquí, separados de la lógica
data class Juego(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val precioOriginal: Double,
    val genero: String,
    val plataforma: String,
    val calificacion: Double,
    val edadRecomendada: String,
    val imagenUrl: String = ""
)

data class Oferta(
    val juego: Juego,
    val descuento: Int,
    val precioConDescuento: Double,
    val ahorro: Double,
    val tiempoRestante: String = ""
)