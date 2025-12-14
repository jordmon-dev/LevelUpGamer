package com.example.levelup_gamer.model

import com.google.gson.annotations.SerializedName

data class PasswordChangeRequest(
    @SerializedName("currentPassword")
    val currentPassword: String,

    @SerializedName("newPassword")
    val newPassword: String,

    @SerializedName("confirmPassword")
    val confirmPassword: String
)