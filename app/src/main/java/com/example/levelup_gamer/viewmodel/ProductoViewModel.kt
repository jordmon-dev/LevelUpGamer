package com.example.levelup_gamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.model.Producto
import com.example.levelup_gamer.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class ProductoViewModel : ViewModel() {

    private val repository = ProductoRepository()

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        cargarProductos()
    }

    fun cargarProductos() {
        viewModelScope.launch {
            _isLoading.value = true
            val lista = repository.obtenerProductos()
            _productos.value = lista
            _isLoading.value = false
        }
    }

    fun agregarProducto(
        nombre: String,
        precio: Int, // Recibe Int
        stock: Int,
        categoria: String,
        imagen: String, // Recibe String (URL)
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true

            // Creamos el producto. Ahora los tipos COINCIDEN con el nuevo Producto.kt
            val nuevoProd = Producto(
                id = 0L,
                nombre = nombre,

                // YA NO necesitamos .toDouble() porque en Producto.kt ahora es Int
                precio = precio,

                stock = stock,
                categoria = categoria,

                // YA NO da error porque en Producto.kt ahora es String?
                imagen = imagen,

                codigo = "COD-${Random.nextInt(1000, 9999)}",
                descripcion = "Agregado desde App Móvil"
            )

            val exito = repository.crearProducto(nuevoProd)

            if (exito) {
                cargarProductos()
            }

            onResult(exito)
            _isLoading.value = false
        }
    }

    fun borrarProducto(id: Long) {
        viewModelScope.launch {
            val exito = repository.eliminarProducto(id)
            if (exito) cargarProductos()
        }
    }
}