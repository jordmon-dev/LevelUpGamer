package com.example.levelup_gamer.modelo

data class UsuarioState(
    val nombre: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val aceptarTerminos: Boolean = false,
    val fechaRegistro: String = "",
    val puntosFidelidad: Int = 0,
    val errores: UsuarioErrores = UsuarioErrores()
)