package com.example.levelup_gamer.model

data class UsuarioState(
    val nombre: String = "",
    val correo: String = "",
    val edad: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val aceptarTerminos: Boolean = false,
    val errores: UsuarioErrores = UsuarioErrores()
)

data class UsuarioErrores(
    val nombre: String? = null,
    val correo: String? = null,
    val edad: String? = null,
    val password: String? = null,
    val confirmPassword: String? = null,
    val aceptaTerminos: String? = null
)