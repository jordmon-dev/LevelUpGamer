package com.example.levelup_gamer.model

import com.google.gson.annotations.SerializedName

data class Producto(
    // 1. El ID es fundamental para identificar qué borrar del carrito
    val id: Int? = null,

    // 2. Usamos valores por defecto ("") para evitar crasheos si el backend manda nulos
    val codigo: String = "",
    val nombre: String,
    val precio: Double,
    val categoria: String = "",
    val descripcion: String = "",

    // 3. El Stock es necesario para validar en el carrito (no vender más de lo que hay)
    val stock: Int = 0,

    // 4. Imagen local (R.drawable...)
    val imagen: Int? = null
)