package com.example.levelup_gamer.model

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("user")
    val user: T? = null,

    @SerializedName("error")
    val error: String? = null
)