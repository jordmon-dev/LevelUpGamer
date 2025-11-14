package com.example.levelup_gamer.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ConfirmacionViewModel : ViewModel() {

    private val _mensaje = MutableStateFlow("✅ ¡Gracias por tu compra en Level-Up Gamer!")
    val mensaje: StateFlow<String> = _mensaje

    fun cambiarMensaje(texto: String) {
        _mensaje.value = texto
    }
}

