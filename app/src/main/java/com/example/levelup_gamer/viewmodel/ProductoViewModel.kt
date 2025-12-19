package com.example.levelup_gamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.model.DatosPrueba // Asegúrate de importar esto
import com.example.levelup_gamer.model.Producto
import com.example.levelup_gamer.remote.RetrofitInstance
import com.example.levelup_gamer.remote.UsuarioInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Estado de UI para filtros
data class CatalogoUiState(
    val busqueda: String = "",
    val categoriaSeleccionada: String = "Todas"
)

class ProductoViewModel : ViewModel() {

    // Lista cruda de productos (Backend o Local)
    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    // Estado de filtros
    private val _uiState = MutableStateFlow(CatalogoUiState())
    val uiState: StateFlow<CatalogoUiState> = _uiState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Categorías para los chips
    val categorias = listOf("Todas", "Consolas", "Accesorios", "Juegos de Mesa", "Computadores", "Sillas", "Mouse", "Poleras")

    init {
        cargarProductos()
    }

    // --- CARGAR PRODUCTOS (HÍBRIDO: ONLINE + OFFLINE) ---
    fun cargarProductos() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // 1. Intentamos conectar al Backend
                val response = RetrofitInstance.api.obtenerProductos()

                if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                    // Éxito: Usamos datos de la nube
                    _productos.value = response.body()!!
                } else {
                    // Fallo del servidor: Usamos datos locales
                    _productos.value = DatosPrueba.listaProductos
                }
            } catch (e: Exception) {
                // Sin internet: Usamos datos locales (Modo Offline)
                _productos.value = DatosPrueba.listaProductos
            } finally {
                _isLoading.value = false
            }
        }
    }

    // --- AGREGAR PRODUCTO (ADMIN) ---
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
                val token = UsuarioInstance.getBearerToken()
                if (token == null) {
                    onResult(false)
                    return@launch
                }

                val nuevoProducto = Producto(
                    nombre = nombre,
                    precio = precio,
                    stock = stock,
                    categoria = categoria,
                    imagen = imagen,
                    descripcion = "Producto agregado desde App Admin"
                )

                val response = RetrofitInstance.api.crearProducto(token, nuevoProducto)

                if (response.isSuccessful) {
                    cargarProductos() // Recargar lista
                    onResult(true)
                } else {
                    onResult(false)
                }
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    // --- FILTROS DE UI ---

    val productosFiltrados: StateFlow<List<Producto>> = combine(_productos, _uiState) { lista, state ->
        lista.filter { producto ->
            val coincideNombre = producto.nombre.contains(state.busqueda, ignoreCase = true)
            val coincideCategoria = if (state.categoriaSeleccionada == "Todas") true else producto.categoria.equals(state.categoriaSeleccionada, ignoreCase = true)
            coincideNombre && coincideCategoria
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun onBusquedaChange(texto: String) {
        _uiState.update { it.copy(busqueda = texto) }
    }

    fun onCategoriaSeleccionada(categoria: String) {
        _uiState.update { it.copy(categoriaSeleccionada = categoria) }
    }
}