package com.example.levelup_gamer.viewmodel


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Modelo de datos para la información 'Acerca de'
data class AboutInfo(
    val mision: String = "Proporcionar productos de alta calidad para gamers en todo Chile, ofreciendo una experiencia de compra única y personalizada, con un enfoque en la satisfacción del cliente y el crecimiento de la comunidad gamer.",
    val vision: String = "Ser la tienda online líder en productos para gamers en Chile, reconocida por su innovación, servicio al cliente excepcional, y un programa de fidelización basado en gamificación que recompense a nuestros clientes más fieles.",
    val email: String = "contacto@levelupgamer.cl",
    val web: String = "www.levelupgamer.cl",
    val telefono: String = "+56 9 1234 5678"
)

class AboutViewModel : ViewModel() {

    private val _info = MutableStateFlow(AboutInfo())
    val info: StateFlow<AboutInfo> = _info.asStateFlow()
}