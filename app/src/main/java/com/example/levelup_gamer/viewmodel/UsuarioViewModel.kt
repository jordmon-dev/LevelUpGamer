package com.example.levelup_gamer.viewmodel

import androidx.lifecycle.ViewModel
import com.example.levelup_gamer.model.UsuarioErrores
import com.example.levelup_gamer.model.UsuarioState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import com.example.levelup_gamer.model.UsuarioPerfil

class UsuarioViewModel: ViewModel() {

    // StateFlow con UsuarioState
    private val _usuario = MutableStateFlow(UsuarioState())
    val usuario: StateFlow<UsuarioState> = _usuario

    // Métodos de cambio
    fun onChangeNombre(nombre: String){
        _usuario.update {
            it.copy(
                nombre = nombre,
                errores = it.errores.copy(nombre = null)
            )
        }
    }

    fun onChangeEdad(edad: String){
        if(edad.all { char -> char.isDigit() } || edad.isEmpty()){
            _usuario.update {
                it.copy(
                    edad = edad,
                    errores = it.errores.copy(edad = null)
                )
            }
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

    fun onChangeConfirmPassword(pass: String){
        _usuario.update {
            it.copy(
                confirmPassword = pass,
                errores = it.errores.copy(confirmPassword = null)
            )
        }
    }

    fun onChangeAceptarTerminos(valor: Boolean){
        _usuario.update {
            it.copy(
                aceptarTerminos = valor,
                errores = it.errores.copy(aceptaTerminos = null)
            )
        }
    }

    fun validar(): Boolean{
        val f = _usuario.value

        // Validación de EDAD
        val edadInt = f.edad.toIntOrNull() ?: 0
        val errorEdad = if (f.edad.isBlank() || edadInt < 18) "Debes ser mayor de 18 años" else null

        // Validación de CONTRASEÑAS COINCIDENTES
        val errorConfirmPass = if (f.password != f.confirmPassword) "Las contraseñas no coinciden" else null

        val errores = UsuarioErrores(
            nombre = if (f.nombre.isBlank()) "El nombre está vacío" else null,
            correo = if (f.correo.isBlank() || !f.correo.contains("@")) "Error en el ingreso de dirección del correo" else null,
            password = if (f.password.isBlank()) "Contraseña vacía" else if (f.password.length < 6) "La contraseña debe tener al menos 6 caracteres" else null,
            edad = errorEdad,
            confirmPassword = errorConfirmPass,
            aceptaTerminos = if (!f.aceptarTerminos) "Debe aceptar los términos y condiciones" else null
        )

        _usuario.update {
            it.copy(errores = errores)
        }

        return errores.nombre == null &&
                errores.correo == null &&
                errores.password == null &&
                errores.edad == null &&
                errores.confirmPassword == null &&
                errores.aceptaTerminos == null
    }

    fun limpiarEstado() {
        _usuario.value = UsuarioState()
    }

    // Método para crear UsuarioPerfil a partir del estado actual
    fun toUsuarioPerfil():UsuarioPerfil {
        val state = _usuario.value
        return UsuarioPerfil(
            nombre = state.nombre,
            email = state.correo,
            puntosLevelUp = 1500,
            nivel = if (state.correo.endsWith("@duocuc.cl")) "Gamer Pro Estudiante" else "Gamer",
            fechaRegistro = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault()).format(java.util.Date())
        )
    }
}