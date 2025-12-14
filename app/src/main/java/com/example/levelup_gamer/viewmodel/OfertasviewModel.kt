package com.example.levelup_gamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
// Importamos el objeto desde el archivo CheapSharkApi.kt
import com.example.levelup_gamer.remote.ExternalRetrofit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// --- MODELOS DE LA UI (Tu estructura visual) ---
data class Juego(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val precioOriginal: Double,
    val genero: String,
    val plataforma: String,
    val calificacion: Double,
    val edadRecomendada: String,
    val imagenUrl: String = "" // <--- URL para la foto
)

data class Oferta(
    val juego: Juego,
    val descuento: Int,
    val precioConDescuento: Double,
    val ahorro: Double,
    val tiempoRestante: String = ""
)

class OfertasViewModel : ViewModel() {

    private val _ofertasState = MutableStateFlow<List<Oferta>>(emptyList())
    val ofertasState: StateFlow<List<Oferta>> = _ofertasState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        cargarOfertas()
    }

    fun cargarOfertas() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // 1. Llamamos a la API (Usando el objeto de CheapSharkApi.kt)
                val apiDeals = ExternalRetrofit.api.getDeals()

                // 2. Transformamos los datos
                val ofertasMapeadas = apiDeals.map { deal ->
                    // Conversión segura de String a números
                    val normal = deal.normalPrice.toDoubleOrNull() ?: 0.0
                    val oferta = deal.salePrice.toDoubleOrNull() ?: 0.0
                    val ahorro = normal - oferta
                    val ahorroInt = deal.savings.toDoubleOrNull()?.toInt() ?: 0 // <--- Ahora sí funciona 'savings'
                    val rating = (deal.steamRatingPercent?.toDoubleOrNull() ?: 0.0) / 20.0 // <--- Y 'steamRatingPercent'

                    Oferta(
                        juego = Juego(
                            id = deal.title.hashCode().toString(),
                            titulo = deal.title,
                            descripcion = "Oferta Steam",
                            precioOriginal = normal,
                            genero = "PC",
                            plataforma = "Steam",
                            calificacion = rating,
                            edadRecomendada = "TBA",
                            imagenUrl = deal.thumb
                        ),
                        descuento = ahorroInt,
                        precioConDescuento = oferta,
                        ahorro = ahorro,
                        tiempoRestante = "24h"
                    )
                }
                _ofertasState.value = ofertasMapeadas

            } catch (e: Exception) {
                // Si falla la red, mostramos lista vacía o local
                _ofertasState.value = emptyList()
                println("Error API: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}