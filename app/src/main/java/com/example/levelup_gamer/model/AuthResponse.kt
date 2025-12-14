package com.example.levelup_gamer.model

import com.example.levelup_gamer.model.Usuario // Asegúrate de importar tu clase Usuario

data class AuthResponse(
    val token: String,
    val usuario: Usuario? = null // Puede ser nulo si el backend falla o cambia
)