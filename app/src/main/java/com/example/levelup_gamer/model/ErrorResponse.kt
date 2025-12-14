package com.example.levelup_gamer.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("success")
    val success: String,

    @SerializedName("error")
    val error: String,

    @SerializedName("message")
    val message: String
)