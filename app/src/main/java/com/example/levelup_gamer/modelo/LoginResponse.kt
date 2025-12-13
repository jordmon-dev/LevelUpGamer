package com.example.levelup_gamer.modelo

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String? = null,
    @SerializedName("token") val token: String? = null,
    @SerializedName("usuario") val usuario: Usuario? = null // Si tu API devuelve el usuario completo
)