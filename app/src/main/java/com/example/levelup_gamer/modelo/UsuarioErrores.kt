package com.example.levelup_gamer.modelo

data class UsuarioErrores(
    var nombre: String = "",  // Cambia de String? a String
    var email: String = "",   // Cambia de String? a String
    var password: String = "", // Cambia de String? a String
    var confirmPassword: String = "", // Cambia de String? a String
    var aceptaTerminos: String = "" // Cambia de String? a String
)