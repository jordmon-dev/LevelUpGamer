package com.example.levelup_gamer.model

import com.google.gson.annotations.SerializedName

data class Usuario(
    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("nombre")
    val nombre: String? = null,

    @SerializedName("apellidos")
    val apellidos: String?= null,

    @SerializedName("email")
    val email: String? = null,

    /*@SerializedName("telefono")
    val telefono: String? = null,*/

    /*
   @SerializedName("fechaCreacion")
   val fechaCreacion: String? = null,*/

    @SerializedName("password")
    val password: String? = null,

    // Agrega más campos según tu backend
    @SerializedName("username")
    val username: String? = null,


    @SerializedName("activo")
    val activo: Boolean? = null,

    @SerializedName("rol")
    val rol: String? = "CLIENTE",

    @SerializedName("direccion")
    val direccion: String? = null,

    @SerializedName("region")
    val region: String? = null,

    @SerializedName("comuna")
    val comuna: String? = null,

)