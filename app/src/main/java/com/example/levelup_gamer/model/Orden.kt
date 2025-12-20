package com.example.levelup_gamer.model

import com.google.gson.annotations.SerializedName

data class Orden(
    @SerializedName("id") val id: Long? = null,
    @SerializedName("numeroOrden") val numeroOrden: Long,
    @SerializedName("total") val total: Int,
    @SerializedName("nombreCliente") val nombreCliente: String,
    @SerializedName("emailCliente") val emailCliente: String,
    // La fecha la pone el backend automáticamente, no es necesario enviarla
    @SerializedName("fecha") val fecha: String? = null,
    @SerializedName("resumenCompra") val resumenCompra: String =""

)