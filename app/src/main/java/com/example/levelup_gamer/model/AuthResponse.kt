package com.example.levelup_gamer.model

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("token")
    val token: String?,

    @SerializedName("usuario")
    val usuario: Usuario?
)