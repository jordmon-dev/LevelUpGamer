package com.example.levelup_gamer.modelo

// Si tu API espera IDs numéricos (Int) en lugar de String
data class Producto(
    val id: Int,              // 1, 2, 3... (o usa String si prefieres "CQ001")
    val codigo: String,       // "CQ001", "AC001", etc. (opcional)
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val stock: Int,
    val imagen: String,          // Para recursos locales (R.drawable)
    val imagenUrl: String? = null, // Para imágenes desde internet
    val categoria: String
)