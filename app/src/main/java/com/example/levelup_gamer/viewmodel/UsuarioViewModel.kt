package com.example.levelup_gamer.viewmodel


import androidx.lifecycle.ViewModel
import com.example.levelup_gamer.model.UsuarioErrores
import com.example.levelup_gamer.model.UsuarioPerfil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class UsuarioViewModel: ViewModel() {

    // Permite la manipulacion en esta clase
    private val _usuario = MutableStateFlow(UsuarioPerfil())

    // permite manipulacion desde Screen
    val usuario: StateFlow<UsuarioPerfil> = _usuario

    // metodos de cambio
    fun onChangeNombre(nombre: String){
        _usuario.update {
            it.copy(
                nombre=nombre,
                errores = it.errores.copy(nombre = null)
            )
        }
    }
    fun onChangeCorreo(correo: String){
        _usuario.update {
            it.copy(
                correo = correo,
                errores = it.errores.copy(correo = null)
            )
        }
    }
    fun onChangePassword(pass: String){
        _usuario.update {
            it.copy(
                password = pass,
                errores = it.errores.copy(password = null)
            )
        }
    }
    fun onChangeAceptarTerminos(valor: Boolean){
        _usuario.update { it.copy(aceptarTerminos = valor) }
    }

    fun validar(): Boolean{
        val f = _usuario.value
        val errores = UsuarioErrores(
            nombre = if (f.nombre.isBlank()) "El nombre está vacío" else null,
            correo = if (f.correo.isBlank() || !f.correo.contains("@")) "Error en el ingreso de dirección del correo" else null,
            password = if (f.password.isBlank()) "pass vacia" else null,
            aceptaTerminos = if (f.aceptarTerminos == false) "debe aceptar" else null

        )
        _usuario.update {
            it.copy(errores = errores)
        }
        if (errores.nombre==null && errores.correo==null && errores.password==null
            && errores.aceptaTerminos==null){
            return  true
        } else{
            return  false
        }

    }
}