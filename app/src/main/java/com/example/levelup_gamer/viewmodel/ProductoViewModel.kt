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

// Estado de la UI para el catálogo
data class CatalogoUiState(
    val busqueda: String = "",
    val categoriaSeleccionada: String = "Todas",
    val isLoading: Boolean = false,
    val error: String? = null
)

class ProductoViewModel(
    private val repository: ProductoRepository = ProductoRepository()
) : ViewModel() {

    // Productos base locales (los que ya usabas, con imágenes de drawable)
    private val productosBase = listOf(
        Producto(
            "CQ001",
            "PlayStation 5",
            549990.0,
            "Consolas",
            "Consola de última generación",
            R.drawable.game_1
        ),
        Producto(
            "AC001",
            "Controlador Xbox Series X",
            59990.0,
            "Accesorios",
            "Control inalámbrico",
            R.drawable.game_2
        ),
        Producto(
            "JM001",
            "Catan",
            29990.0,
            "Juegos de Mesa",
            "Juego de estrategia",
            R.drawable.game_3
        ),
        Producto(
            "CG001",
            "PC Gamer ASUS ROG",
            1299990.0,
            "Computadores",
            "Equipo de alto rendimiento",
            R.drawable.game_4
        ),
        Producto(
            "SG001",
            "Silla Gamer SecretLab",
            349990.0,
            "Sillas",
            "Máximo confort para largas partidas",
            R.drawable.game_5
        ),
        Producto(
            "MS001",
            "Mouse Logitech G502",
            49990.0,
            "Mouse",
            "Alta precisión para gamers",
            R.drawable.game_6
        ),
        Producto(
            "PP001",
            "Polera Gamer Personalizada",
            14990.0,
            "Poleras",
            "Polera con diseño gamer personalizable",
            R.drawable.game_7
        )
    )

    // Categorías usadas en el LazyRow de chips en CatalogoScreen
    val categorias = listOf(
        "Todas",
        "Consolas",
        "Accesorios",
        "Juegos de Mesa",
        "Computadores",
        "Sillas",
        "Mouse",
        "Poleras"
    )

    // Estado de la UI (búsqueda, categoría, loading, error)
    private val _uiState = MutableStateFlow(CatalogoUiState())
    val uiState: StateFlow<CatalogoUiState> = _uiState.asStateFlow()

    // Productos que vienen de la API (null = aún no cargados / error)
    private val _productosRemotos = MutableStateFlow<List<Producto>?>(null)

    /**
     * Lista final que consume la UI (CatalogoScreen).
     * Si hay productos remotos, se priorizan.
     * Si no, se usan los productos locales (productosBase).
     */
    val productosFiltrados: StateFlow<List<Producto>> =
        combine(_uiState, _productosRemotos) { state, productosRemotos ->
            val fuente = productosRemotos ?: productosBase

            fuente.filter { producto ->
                val coincideCategoria =
                    state.categoriaSeleccionada == "Todas" ||
                            producto.categoria == state.categoriaSeleccionada

                val coincideTexto =
                    producto.nombre.contains(state.busqueda, ignoreCase = true) ||
                            producto.descripcion.contains(state.busqueda, ignoreCase = true)

                coincideCategoria && coincideTexto
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = productosBase
        )

    init {
        // Si quieres que llame a la API al entrar al catálogo, deja esto:
        cargarProductosDesdeApi()
        // Si prefieres que solo cargue al presionar un botón "Actualizar",
        // puedes comentar la línea de arriba y llamar a esta función desde la UI.
    }

    /**
     * Llama al backend vía Retrofit y actualiza el estado.
     * Si la llamada falla, se muestra el error pero la app sigue usando productosBase.
     */
    fun cargarProductosDesdeApi() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val lista = repository.obtenerProductos()
                _productosRemotos.value = lista
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = e.message ?: "Error al obtener productos desde la API"
                    )
                }
                // En caso de error, seguimos usando productosBase como respaldo.
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    // Métodos que ya usan tus pantallas (no cambiamos la interfaz)

    fun onBusquedaChange(texto: String) {
        _uiState.update { it.copy(busqueda = texto) }
    }

    fun onCategoriaSeleccionada(categoria: String) {
        _uiState.update { it.copy(categoriaSeleccionada = categoria) }
    }
}
