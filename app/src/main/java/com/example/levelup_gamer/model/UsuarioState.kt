package com.example.levelup_gamer.model

data class UsuarioState(
    val nombre: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val aceptarTerminos: Boolean = false,

    // Datos del Perfil
    val nivel: Int = 1,
    val puntosLevelUp: Int = 0,
    val fechaRegistro: String = "",

    val errores: UsuarioErrores = UsuarioErrores()
)
