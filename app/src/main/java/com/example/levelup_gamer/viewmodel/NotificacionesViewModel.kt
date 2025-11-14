package com.example.levelup_gamer.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NotificacionesViewModel : ViewModel() {

    private val _notificaciones = MutableStateFlow(
        listOf(
            "🔥 10% de descuento en teclados gamer",
            "🎮 Nuevo producto: consola portátil Neo X",
            "⭐ Participa en nuestro torneo semanal",
            "💬 Soporte técnico respondió tu solicitud"
        )
    )
    val notificaciones: StateFlow<List<String>> = _notificaciones

    fun agregarNotificacion(nueva: String) {
        _notificaciones.value = listOf(nueva) + _notificaciones.value
    }
}
