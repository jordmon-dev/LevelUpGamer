package com.example.levelup_gamer.model

// clase con los atributos del usuario
data class UsuarioPerfil(
    val nombre: String="",
    val correo: String="",
    val password: String="",
    val aceptarTerminos: Boolean = false,
    val errores: UsuarioErrores = UsuarioErrores(),
    val edad: String="",
    val email: String="",
    val nivel: String="",
    val fechaRegistro:String="",
    val puntosLevelUp: Int=0,
    val confirmPassword: String=""
)

// almacena los errores de cada atributo
data class UsuarioErrores(
    val nombre: String? = null,
    val correo: String? = null,
    val password: String? = null,
    val aceptaTerminos: String? =null,
    val edad: String? ="",
    val confirmPassword: String? = null

    )