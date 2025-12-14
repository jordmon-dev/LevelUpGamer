package com.example.levelup_gamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.R
import com.example.levelup_gamer.model.Producto
import com.example.levelup_gamer.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Estado de la UI
data class CatalogoUiState(
    val busqueda: String = "",
    val categoriaSeleccionada: String = "Todas"
)

class ProductoViewModel : ViewModel() {

    // Usamos el repositorio para conectar con el Backend
    private val repository = ProductoRepository()

    // Lista de productos que viene del servidor
    private val _productosBackend = MutableStateFlow<List<Producto>>(emptyList())

    // Estado de filtros
    private val _uiState = MutableStateFlow(CatalogoUiState())
    val uiState: StateFlow<CatalogoUiState> = _uiState.asStateFlow()

    // Categorías fijas para el filtro
    val categorias = listOf("Todas", "Consolas", "Accesorios", "Juegos de Mesa", "Computadores", "Sillas", "Mouse", "Poleras")

    init {
        cargarProductos()
    }

    private fun cargarProductos() {
        viewModelScope.launch {
            // Llamada asíncrona al repositorio
            val lista = repository.obtenerProductos()

            // Asignamos imágenes locales según la categoría para que se vea bonito
            // (Ya que el backend probablemente no envía el ID de la imagen de Android)
            val listaConImagenes = lista.map { p ->
                p.copy(imagen = asignarImagenPorCategoria(p.categoria))
            }

            _productosBackend.value = listaConImagenes
        }
    }

    private fun asignarImagenPorCategoria(categoria: String?): Int {
        return when (categoria) {
            "Consolas" -> R.drawable.game_1
            "Accesorios" -> R.drawable.game_2
            "Juegos de Mesa" -> R.drawable.game_3
            "Computadores" -> R.drawable.game_4
            "Sillas" -> R.drawable.game_5
            "Mouse" -> R.drawable.game_6
            "Poleras" -> R.drawable.game_7
            else -> R.drawable.game_1 // Default
        }
    }

    // Filtro automático: Combina la lista del backend con el texto de búsqueda
    val productosFiltrados: StateFlow<List<Producto>> = _uiState.combine(_productosBackend) { state, productos ->
        if (productos.isEmpty()) {
            emptyList()
        } else {
            productos.filter { producto ->
                (state.categoriaSeleccionada == "Todas" || producto.categoria == state.categoriaSeleccionada) &&
                        (producto.nombre.contains(state.busqueda, ignoreCase = true) ||
                                producto.descripcion.contains(state.busqueda, ignoreCase = true))
            }
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