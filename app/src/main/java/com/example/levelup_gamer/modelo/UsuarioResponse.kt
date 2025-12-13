package com.example.levelup_gamer.modelo

import com.google.gson.annotations.SerializedName

// Respuesta para login
data class LoginResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String? = null,
    @SerializedName("token") val token: String? = null,
    @SerializedName("nombre") val nombre: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("fechaRegistro") val fechaRegistro: String? = null,
    @SerializedName("puntosFidelidad") val puntosFidelidad: Int? = null
)

// Respuesta para registro
data class RegisterResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String? = null,
    @SerializedName("token") val token: String? = null,
    @SerializedName("fechaRegistro") val fechaRegistro: String? = null,
    @SerializedName("puntosFidelidad") val puntosFidelidad: Int? = null
)

// Respuesta para obtener perfil
data class PerfilResponse(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("nombre") val nombre: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("fechaRegistro") val fechaRegistro: String? = null,
    @SerializedName("puntosFidelidad") val puntosFidelidad: Int? = null,
    @SerializedName("telefono") val telefono: String? = null,
    @SerializedName("direccion") val direccion: String? = null,
    @SerializedName("imagen") val imagen: String? = null
)

// Modelo Usuario para usar en la app
