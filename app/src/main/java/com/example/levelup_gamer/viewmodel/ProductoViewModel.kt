package com.example.levelup_gamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.model.Producto
import com.example.levelup_gamer.repository.ProductoRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.random.Random

// Estado para el buscador y filtros
data class CatalogoUiState(
    val busqueda: String = "",
    val categoriaSeleccionada: String = "Todas"
)

class ProductoViewModel : ViewModel() {

    private val repository = ProductoRepository()

    // 1. Lista cruda (todos los productos del servidor)
    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    // 2. Estado de la UI (Buscador y Categoría)
    private val _uiState = MutableStateFlow(CatalogoUiState())
    val uiState: StateFlow<CatalogoUiState> = _uiState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // 3. Categorías disponibles para los Chips
    val categorias = listOf("Todas", "PS5", "Xbox", "Switch", "PC")

    // 4. LÓGICA DE FILTRADO AUTOMÁTICO
    // Combina la lista original + el texto del buscador + la categoría seleccionada
    val productosFiltrados: StateFlow<List<Producto>> = combine(_productos, _uiState) { lista, state ->
        lista.filter { producto ->
            // Filtro por nombre (Buscador)
            val coincideNombre = producto.nombre.contains(state.busqueda, ignoreCase = true)

            // Filtro por categoría
            val coincideCategoria = if (state.categoriaSeleccionada == "Todas") {
                true
            } else {
                producto.categoria.equals(state.categoriaSeleccionada, ignoreCase = true)
            }

            coincideNombre && coincideCategoria
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        cargarProductos()
    }

    // --- FUNCIONES DEL BACKEND ---

    fun cargarProductos() {
        viewModelScope.launch {
            _isLoading.value = true
            // Aquí puedes añadir tu lógica de "DatosPrueba" si falla internet
            try {
                val lista = repository.obtenerProductos()
                if (lista.isNotEmpty()) {
                    _productos.value = lista
                } else {
                    // Fallback si está vacío (Opcional, si usas DatosPrueba)
                    _productos.value = emptyList()
                }
            } catch (e: Exception) {
                _productos.value = emptyList()
            }
            _isLoading.value = false
        }
    }

    fun agregarProducto(
        nombre: String,
        precio: Int,
        stock: Int,
        categoria: String,
        imagen: String,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            val nuevoProd = Producto(
                id = 0L,
                nombre = nombre,
                precio = precio,
                stock = stock,
                categoria = categoria,
                imagen = imagen,
                codigo = "COD-${Random.nextInt(1000, 9999)}",
                descripcion = "Agregado desde App Móvil"
            )

            val exito = repository.crearProducto(nuevoProd)
            if (exito) cargarProductos()
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

    // --- FUNCIONES DE LA UI (Buscador y Filtros) ---

    fun onBusquedaChange(texto: String) {
        _uiState.update { it.copy(busqueda = texto) }
    }

    fun onCategoriaSeleccionada(categoria: String) {
        _uiState.update { it.copy(categoriaSeleccionada = categoria) }
    }
}