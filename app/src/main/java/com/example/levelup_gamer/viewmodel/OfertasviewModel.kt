package com.example.levelup_gamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.remote.ExternalRetrofit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Modelos simples para la vista
data class Juego(val titulo: String, val imagenUrl: String, val precio: String)
data class Oferta(val juego: Juego, val precioOferta: String, val descuento: String)

class OfertasViewModel : ViewModel() {

    private val _ofertasState = MutableStateFlow<List<Oferta>>(emptyList())
    val ofertasState: StateFlow<List<Oferta>> = _ofertasState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        cargarOfertas()
    }

    fun cargarOfertas() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // 1. Llamamos a CheapShark
                val listaApi = ExternalRetrofit.api.getDeals()

                // 2. Traducimos ApiDeal -> Oferta
                val listaLimpia = listaApi.map { apiDeal ->
                    Oferta(
                        juego = Juego(apiDeal.title, apiDeal.thumb, apiDeal.normalPrice),
                        precioOferta = apiDeal.salePrice,
                        descuento = apiDeal.savings.substringBefore(".") // Quitamos decimales extra
                    )
                }
                _ofertasState.value = listaLimpia

            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}