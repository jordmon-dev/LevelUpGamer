package com.example.levelup_gamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
// Importamos tus modelos desde la carpeta correcta
import com.example.levelup_gamer.model.Juego
import com.example.levelup_gamer.model.Oferta
import com.example.levelup_gamer.remote.ExternalRetrofit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OfertasViewModel : ViewModel() {

    private val _ofertasState = MutableStateFlow<List<Oferta>>(emptyList())
    val ofertasState: StateFlow<List<Oferta>> = _ofertasState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Variable para capturar errores
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        obtenerOfertasDesdeApi()
    }

    fun obtenerOfertasDesdeApi() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                // Llamada a la API
                val apiDeals = ExternalRetrofit.api.getDeals()

                if (apiDeals.isEmpty()) {
                    _errorMessage.value = "La API respondió, pero no hay ofertas."
                }

                val ofertasMapeadas = apiDeals.map { deal ->
                    val normal = deal.normalPrice.toDoubleOrNull() ?: 0.0
                    val sale = deal.salePrice.toDoubleOrNull() ?: 0.0
                    val savings = deal.savings.toDoubleOrNull()?.toInt() ?: 0

                    Oferta(
                        juego = Juego(
                            id = deal.title.hashCode().toString(),
                            titulo = deal.title,
                            descripcion = "Oferta de Steam",
                            precioOriginal = normal,
                            genero = "Digital",
                            plataforma = "PC / Steam",
                            calificacion = 5.0,
                            edadRecomendada = "T",
                            imagenUrl = deal.thumb
                        ),
                        descuento = savings,
                        precioConDescuento = sale,
                        ahorro = normal - sale,
                        tiempoRestante = "24h"
                    )
                }
                _ofertasState.value = ofertasMapeadas

            } catch (e: Exception) {
                _errorMessage.value = "ERROR: ${e.localizedMessage}"
                println("API FALLO: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}