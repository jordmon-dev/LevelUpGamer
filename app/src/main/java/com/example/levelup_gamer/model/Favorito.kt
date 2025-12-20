package com.example.levelup_gamer.model

import com.google.gson.annotations.SerializedName

data class Favorito(
    @SerializedName("id") val id: Long,
    @SerializedName("producto") val producto: Producto // Reutilizamos tu modelo Producto existente
)