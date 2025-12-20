package com.example.levelup_gamer.model

import com.google.gson.annotations.SerializedName

data class FavoritoDto(
    @SerializedName("email") val email: String,
    @SerializedName("productoId") val productoId: Long
)