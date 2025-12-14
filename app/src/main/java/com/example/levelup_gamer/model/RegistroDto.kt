package com.example.levelup_gamer.model

data class RegistroDto(
    val nombre: String,
    val apellidos: String,
    val email: String,
    val password: String,
    val direccion: String,
    val region: String,
    val comuna: String
)