package com.example.levelup_gamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.model.Producto
import com.example.levelup_gamer.remote.RetrofitInstance
import com.example.levelup_gamer.remote.UsuarioInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductoViewModel : ViewModel() {

    // Estado para la lista de productos (Catálogo)
    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        cargarProductos()
    }

    // Cargar catálogo (Público)
    fun cargarProductos() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitInstance.api.obtenerProductos()
                if (response.isSuccessful) {
                    _productos.value = response.body() ?: emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ✅ FUNCIÓN CORREGIDA: AGREGAR PRODUCTO (CON TOKEN)
    fun agregarProducto(
        nombre: String,
        precio: Int,
        stock: Int,
        categoria: String,
        imagen: String,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            try {
                // 1. Obtenemos el Token del Admin
                val token = UsuarioInstance.getBearerToken()

                if (token == null) {
                    // Si no hay token, fallamos inmediatamente
                    onResult(false)
                    return@launch
                }

                // 2. Creamos el objeto
                val nuevoProducto = Producto(
                    nombre = nombre,
                    precio = precio,
                    stock = stock,
                    categoria = categoria,
                    imagen = imagen,
                    descripcion = "Producto agregado desde App Admin"
                )

                // 3. Enviamos la petición CON EL TOKEN
                val response = RetrofitInstance.api.crearProducto(token, nuevoProducto)

                if (response.isSuccessful) {
                    cargarProductos() // Recargamos la lista para ver el cambio
                    onResult(true)
                } else {
                    onResult(false)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(false)
            }
        }
    }
}