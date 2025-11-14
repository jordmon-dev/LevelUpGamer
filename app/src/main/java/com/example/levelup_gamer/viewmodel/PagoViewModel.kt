package com.example.levelup_gamer.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PagoViewModel : ViewModel() {

    private val _total = MutableStateFlow(0.0)
    val total: StateFlow<Double> = _total

    private val _pagoExitoso = MutableStateFlow(false)
    val pagoExitoso: StateFlow<Boolean> = _pagoExitoso

    fun actualizarTotal(nuevoTotal: Double) {
        _total.value = nuevoTotal
    }

    fun confirmarPago() {
        _pagoExitoso.value = true
    }

    fun reiniciarPago() {
        _pagoExitoso.value = false
        _total.value = 0.0
    }
}


