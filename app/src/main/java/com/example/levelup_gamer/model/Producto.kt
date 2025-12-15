package com.example.levelup_gamer.model

import com.google.gson.annotations.SerializedName

data class Producto(
    // 1. ID debe ser Long porque en MySQL es autoincrementable grande
    val id: Int = 0,

    // 2. Código del producto
    val codigo: String = "",

    val nombre: String,

    // 3. CAMBIO IMPORTANTE: El backend usa Integer, aquí usamos Int
    val precio: Int,

    val categoria: String = "",
    val descripcion: String = "",

    val stock: Int = 0,

    // 4. CAMBIO IMPORTANTE: Imagen ahora es String para guardar URLs (http://...)
    // Usamos @SerializedName para asegurarnos que coincida con el JSON del backend si se llama diferente,
    // pero en tu backend también se llama "imagen", así que está bien.
    val imagen: String? = null
)