package com.example.levelup_gamer.viewmodel

data class UsuarioState(
    val nombre: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val aceptarTerminos: Boolean = false,
    val fechaRegistro: String = "",
    val errores: UsuarioErrores = UsuarioErrores()
)
