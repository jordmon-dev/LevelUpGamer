package com.example.levelup_gamer.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.model.Producto // <-- Importa tu modelo Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

// 1. Definición del Estado de la UI para el Catálogo
data class CatalogoUiState(
    val busqueda: String = "",
    val categoriaSeleccionada: String = "Todas"
)

class ProductoViewModel : ViewModel() {

    // Lista de productos base (Hardcodeado basado en el brief)
    private val productosBase = listOf(
        Producto("CQ001", "PlayStation 5", 549990.0, "Consolas", "Consola de última generación"),
        Producto("AC001", "Controlador Xbox Series X", 59990.0, "Accesorios", "Control inalámbrico"),
        Producto("JM001", "Catan", 29990.0, "Juegos de Mesa", "Juego de estrategia"),
        Producto("CG001", "PC Gamer ASUS ROG", 1299990.0, "Computadores", "Alto rendimiento"),
        Producto("SG001", "Silla Gamer SecretLab", 349990.0, "Sillas", "Máximo confort"),
        Producto("MS001", "Mouse Logitech G502", 49990.0, "Mouse", "Alta precisión"),
        Producto("PP001", "Polera Gamer Personalizada", 14990.0, "Poleras", "Personalizable")
    )

    // Lista de todas las categorías disponibles
    val categorias = listOf("Todas", "Consolas", "Accesorios", "Juegos de Mesa", "Computadores", "Sillas", "Mouse", "Poleras")

    private val _uiState = MutableStateFlow(CatalogoUiState())
    val uiState: StateFlow<CatalogoUiState> = _uiState.asStateFlow()

    // 2. Flujo de datos filtrados (Lógica de filtrado)
    val productosFiltrados: StateFlow<List<Producto>> = _uiState.combine(_uiState) { state, _ ->
        productosBase.filter { producto ->
            (state.categoriaSeleccionada == "Todas" || producto.categoria == state.categoriaSeleccionada) &&
                    (producto.nombre.contains(state.busqueda, ignoreCase = true) ||
                            producto.descripcion.contains(state.busqueda, ignoreCase = true))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = productosBase
    )

    // 3. Métodos para actualizar el estado desde la UI (Buscador/Filtros)
    fun onBusquedaChange(texto: String) {
        _uiState.update { it.copy(busqueda = texto) }
    }

    fun onCategoriaSeleccionada(categoria: String) {
        _uiState.update { it.copy(categoriaSeleccionada = categoria) }
    }
}