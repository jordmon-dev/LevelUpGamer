package com.example.levelup_gamer.model

data class UsuarioState(
    val nombre: String = "",
    val apellidos: String = "",
    val email: String = "",
    val password: String = "",

    //Campos de lógica registro/UI
    val confirmPassword: String = "",
    val aceptarTerminos: Boolean = false,

    // Datos de envío o CHECKOUT
    val direcion: String = "",
    val region: String = "",
    val comuna: String = "",

    // Datos del Perfil
    val nivel: Int = 1,
    val puntosLevelUp: Int = 0,
    val fechaRegistro: String = "",

    //Campos de seguridad (roles)
    val rol: String = "CLIENTE",

    val errores: UsuarioErrores = UsuarioErrores()
)

