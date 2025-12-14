package com.example.levelup_gamer.model

data class UsuarioErrores(
    // CAMBIO: Usar "" en lugar de null para evitar errores en la UI
    var nombre: String = "",
    var email: String = "",
    var password: String = "",
    var confirmPassword: String = "",
    var aceptaTerminos: String = ""
)