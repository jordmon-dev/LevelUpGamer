package com.example.levelup_gamer.model


import com.google.gson.annotations.SerializedName

data class GameDeal(
    @SerializedName("title") val title: String,
    @SerializedName("salePrice") val salePrice: String,
    @SerializedName("normalPrice") val normalPrice: String,
    @SerializedName("thumb") val thumb: String, // URL de la imagen del juego
    @SerializedName("steamRatingPercent") val steamRating: String? = "0"
)