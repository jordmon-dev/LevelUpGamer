package com.example.levelup_gamer.modelo

import com.google.gson.annotations.SerializedName

data class Usuario(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("nombre") val nombre: String = "", // Cambia de nombreCompleto a nombre
    @SerializedName("email") val email: String = "",
    @SerializedName("password") val password: String? = null,
    @SerializedName("token") val token: String? = null,
    @SerializedName("avatar") val avatar: String? = null,
    @SerializedName("puntosFidelidad") val puntosFidelidad: Int = 0,
    @SerializedName("fechaRegistro") val fechaRegistro: String = "",
    @SerializedName("telefono") val telefono: String? = null,
    @SerializedName("direccion") val direccion: String? = null,
    @SerializedName("preferencias") val preferencias: List<String> = emptyList()
)