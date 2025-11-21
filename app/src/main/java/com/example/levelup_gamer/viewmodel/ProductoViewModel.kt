package com.example.levelup_gamer.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import com.example.levelup_gamer.R // ⬅️ FIX 1: Importar la clase R para acceder a los recursos

// 1. Definición del Estado de la UI para el Catálogo
data class CatalogoUiState(
    val busqueda: String = "",
    val categoriaSeleccionada: String = "Todas"
)

class ProductoViewModel : ViewModel() {

    // Lista de productos base (Hardcodeado con IMÁGENES)
    private val productosBase = listOf(
        // Aquí se usará R.drawable.game_X. Asegúrate de que las imágenes game_1.png a game_7.png estén en /res/drawable
        Producto("CQ001", "PlayStation 5", 549990.0, "Consolas", "Consola de última generación", R.drawable.game_1),
        Producto("AC001", "Controlador Xbox Series X", 59990.0, "Accesorios", "Control inalámbrico", R.drawable.game_2),
        Producto("JM001", "Catan", 29990.0, "Juegos de Mesa", "Juego de estrategia", R.drawable.game_3),
        Producto("CG001", "PC Gamer ASUS ROG", 1299990.0, "Computadores", "Alto rendimiento", R.drawable.game_4), // ⬅️ Producto visible en tu captura
        Producto("SG001", "Silla Gamer SecretLab", 349990.0, "Sillas", "Máximo confort", R.drawable.game_5),
        Producto("MS001", "Mouse Logitech G502", 49990.0, "Mouse", "Alta precisión", R.drawable.game_6),
        Producto("PP001", "Polera Gamer Personalizada", 14990.0, "Poleras", "Personalizable", R.drawable.game_7)
    )

    // Lista de todas las categorías disponibles
    val categorias = listOf("Todas", "Consolas", "Accesorios", "Juegos de Mesa", "Computadores", "Sillas", "Mouse", "Poleras")

    private val _uiState = MutableStateFlow(CatalogoUiState())
    val uiState: StateFlow<CatalogoUiState> = _uiState.asStateFlow()

    // 2. Flujo de datos filtrados (Lógica de filtrado)
    // FIX 2: Volver a la estructura .combine / .stateIn para reactividad y evitar el casting problemático.
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