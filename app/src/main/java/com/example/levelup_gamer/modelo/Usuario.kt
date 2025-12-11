package com.example.levelup_gamer.modelo

data class Usuario(
    val id: Int? = null,
    val email: String,
    val nombreCompleto: String,
    val password: String? = null, // Solo para registro/login
    val token: String? = null,    // Para sesión
    val avatar: String? = null,
    val puntosFidelidad: Int = 0,
    val preferencias: List<String> = emptyList()
)