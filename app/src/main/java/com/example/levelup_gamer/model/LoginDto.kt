package com.example.levelup_gamer.model

data class LoginDto(
    val email: String,    // Tu backend espera "email", no "username"
    val password: String
)