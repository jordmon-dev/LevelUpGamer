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

    fun onChangeEdad(edad:String){
        if(edad.all { char -> char.isDigit() }){
            _usuario.update {
                it.copy(
                    edad=edad,
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

    fun onChangeConfirmPassword(pass: String){ // <-- Asumo que esta función te falta o está incompleta
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
        val edadInt =f.edad.toIntOrNull() ?:0
        val errorEdad = if (f.edad.isBlank() || edadInt < 18) "Debes ser mayor de 18 años" else null

        // Validación de CONTRASEÑAS COINCIDENTES
        val errorConfirmPass = if (f.password != f.confirmPassword) "Las contraseñas no coinciden" else null

        val errores = UsuarioErrores(
            nombre = if (f.nombre.isBlank()) "El nombre está vacío" else null,
            correo = if (f.correo.isBlank() || !f.correo.contains("@")) "Error en el ingreso de dirección del correo" else null,
            password = if (f.password.isBlank()) "Contraseña vacía" else null,
            edad = errorEdad,
            confirmPassword = errorConfirmPass, // <- Se asume que este campo ya existe en UsuarioErrores
            aceptaTerminos = if (f.aceptarTerminos == false) "Debe aceptar los términos y condiciones" else null
        )

        _usuario.update {
            it.copy(errores = errores)
        }

        // Se comprueban todos los errores (incluyendo el nuevo de confirmPassword)
        return errores.nombre==null && errores.correo==null && errores.password==null
                && errores.edad==null && errores.confirmPassword==null && errores.aceptaTerminos==null
    }

    fun limpiarUsuario(){
        _usuario.update {
            UsuarioPerfil()
        }
    }
}