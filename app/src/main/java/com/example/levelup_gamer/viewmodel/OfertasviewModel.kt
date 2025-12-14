package com.example.levelup_gamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.remote.ExternalRetrofit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// 1. Agregamos 'imagenUrl' a tu modelo existente
data class Juego(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val precioOriginal: Double,
    val genero: String,
    val plataforma: String,
    val calificacion: Double,
    val edadRecomendada: String,
    val imagenUrl: String = "" // <--- NUEVO CAMPO PARA LA FOTO DE LA API
)

data class Oferta(
    val juego: Juego,
    val descuento: Int,
    val precioConDescuento: Double,
    val ahorro: Double,
    val tiempoRestante: String = ""
)

class OfertasViewModel : ViewModel() {

    // Usamos StateFlow para que la pantalla se actualice sola cuando lleguen los datos
    private val _ofertasState = MutableStateFlow<List<Oferta>>(emptyList())
    val ofertasState: StateFlow<List<Oferta>> = _ofertasState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        obtenerOfertasDesdeApi()
    }

    fun obtenerOfertasDesdeApi() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // 1. Llamamos a la API real
                val apiDeals = ExternalRetrofit.api.getDeals()

                // 2. Convertimos los datos de la API a TU modelo 'Oferta'
                val ofertasMapeadas = apiDeals.map { deal ->
                    val precioNormal = deal.normalPrice.toDoubleOrNull() ?: 0.0
                    val precioOferta = deal.salePrice.toDoubleOrNull() ?: 0.0
                    val ahorro = precioNormal - precioOferta
                    val descuento = deal.savings.toDoubleOrNull()?.toInt() ?: 0

                    Oferta(
                        juego = Juego(
                            id = deal.title.hashCode().toString(),
                            titulo = deal.title,
                            descripcion = "Oferta de Steam por tiempo limitado",
                            precioOriginal = precioNormal,
                            genero = "PC Digital",
                            plataforma = "Steam",
                            calificacion = (deal.steamRatingPercent?.toDoubleOrNull() ?: 0.0) / 20.0, // Escala 0-5
                            edadRecomendada = "TBA",
                            imagenUrl = deal.thumb // <--- Guardamos la URL aquí
                        ),
                        descuento = descuento,
                        precioConDescuento = precioOferta,
                        ahorro = ahorro,
                        tiempoRestante = "24h"
                    )
                }
                _ofertasState.value = ofertasMapeadas

            } catch (e: Exception) {
                // Si falla (sin internet), cargamos los datos locales de respaldo
                _ofertasState.value = obtenerOfertasLocales()
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Tu función antigua queda como respaldo (Fallback)
    private fun obtenerOfertasLocales(): List<Oferta> {
        return listOf(
            Oferta(
                juego = Juego(
                    id = "1",
                    titulo = "The Witcher 3: Wild Hunt",
                    descripcion = "RPG épico",
                    precioOriginal = 39.99,
                    genero = "RPG",
                    plataforma = "PC, PS4",
                    calificacion = 4.9,
                    edadRecomendada = "M",
                    imagenUrl = ""
                ),
                descuento = 50,
                precioConDescuento = 19.99,
                ahorro = 20.00,
                tiempoRestante = "2 días"
            )
        )
    }
}