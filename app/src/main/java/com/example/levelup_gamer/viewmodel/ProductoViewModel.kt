package com.example.levelup_gamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.modelo.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import com.example.levelup_gamer.R

// 1. Definición del Estado de la UI para el Catálogo
data class CatalogoUiState(
    val busqueda: String = "",
    val categoriaSeleccionada: String = "Todas"
)

class ProductoViewModel : ViewModel() {

    // Lista de productos base (Hardcodeado con IMÁGENES)
    private val productosBase = listOf(
        Producto(
            id = 1,
            codigo = "CQ001",
            nombre = "PlayStation 5",
            descripcion = "Consola de última generación",
            precio = 549990.0,
            stock = 54,
            imagen = R.drawable.game_1,
            categoria = "Consolas"
        ),
        Producto(
            id = 2,
            codigo = "AC001",
            nombre = "Controlador Xbox Series X",
            descripcion = "Control inalámbrico",
            precio = 59990.0,
            stock = 30,
            imagen = R.drawable.game_2,
            categoria = "Accesorios"
        ),
        Producto(
            id = 3,
            codigo = "JM001",
            nombre = "Catan",
            descripcion = "Juego de estrategia",
            precio = 29990.0,
            stock = 25,
            imagen = R.drawable.game_3,
            categoria = "Juegos de Mesa"
        ),
        Producto(
            id = 4,
            codigo = "CG001",
            nombre = "PC Gamer ASUS ROG",
            descripcion = "Alto rendimiento",
            precio = 1299990.0,
            stock = 10,
            imagen = R.drawable.game_4,
            categoria = "Computadores"
        ),
        Producto(
            id = 5,
            codigo = "SG001",
            nombre = "Silla Gamer SecretLab",
            descripcion = "Máximo confort",
            precio = 349990.0,
            stock = 15,
            imagen = R.drawable.game_5,
            categoria = "Sillas"
        ),
        Producto(
            id = 6,
            codigo = "MS001",
            nombre = "Mouse Logitech G502",
            descripcion = "Alta precisión",
            precio = 49990.0,
            stock = 50,
            imagen = R.drawable.game_6,
            categoria = "Mouse"
        ),
        Producto(
            id = 7,
            codigo = "PP001",
            nombre = "Polera Gamer Personalizada",
            descripcion = "Personalizable",
            precio = 14990.0,
            stock = 100,
            imagen = R.drawable.game_7,
            categoria = "Poleras"
        )
    )

    // Lista de todas las categorías disponibles
    val categorias = listOf("Todas", "Consolas", "Accesorios", "Juegos de Mesa", "Computadores", "Sillas", "Mouse", "Poleras")

    private val _uiState = MutableStateFlow(CatalogoUiState())
    val uiState: StateFlow<CatalogoUiState> = _uiState.asStateFlow()

    // Flujo de datos filtrados usando map para mayor eficiencia
    val productosFiltrados: StateFlow<List<Producto>> = uiState.map { state ->
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

    // Métodos para actualizar el estado desde la UI (Buscador/Filtros)
    fun onBusquedaChange(texto: String) {
        _uiState.update { it.copy(busqueda = texto) }
    }

    fun onCategoriaSeleccionada(categoria: String) {
        _uiState.update { it.copy(categoriaSeleccionada = categoria) }
    }
}