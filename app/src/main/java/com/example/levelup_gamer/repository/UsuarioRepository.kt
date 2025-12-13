package com.example.levelup_gamer.repository

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.levelup_gamer.modelo.Usuario
import com.example.levelup_gamer.remote.RetrofitInstance
import com.example.levelup_gamer.remote.UsuarioApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class UsuarioRepository(private val application: Application) {

    private val usuarioApiService: UsuarioApiService by lazy {
        RetrofitInstance.usuarioApiService
    }

    private val sharedPreferences by lazy {
        application.getSharedPreferences("usuario_prefs", Context.MODE_PRIVATE)
    }

    // ------------------------------
    // AUTENTICACIÓN (ajustado a tu UsuarioApiService)
    // ------------------------------
    suspend fun login(email: String, password: String): Result<Usuario> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("UsuarioRepository", "Intentando login para: $email")

                // Tu API espera un Map, no un objeto LoginRequest
                val loginData = mapOf(
                    "email" to email,
                    "password" to password
                )

                val response = usuarioApiService.login(loginData)

                if (response.isSuccessful) {
                    val usuario = response.body()
                    if (usuario != null) {
                        // Guardar token si existe
                        usuario.token?.let { token ->
                            saveToken(token)
                            saveUserEmail(email)
                        }

                        Log.d("UsuarioRepository", "Login exitoso para: $email")
                        Result.success(usuario)
                    } else {
                        Result.failure(Exception("Credenciales incorrectas"))
                    }
                } else {
                    val errorMsg = when (response.code()) {
                        401 -> "Credenciales incorrectas"
                        404 -> "Usuario no encontrado"
                        500 -> "Error del servidor"
                        else -> "Error: ${response.code()}"
                    }
                    Log.e("UsuarioRepository", "Error HTTP: ${response.code()}")
                    Result.failure(Exception(errorMsg))
                }
            } catch (e: IOException) {
                Log.e("UsuarioRepository", "Error de conexión: ${e.message}")
                Result.failure(Exception("Error de conexión. Verifica tu internet."))
            } catch (e: HttpException) {
                Log.e("UsuarioRepository", "Error HTTP: ${e.message}")
                Result.failure(Exception("Error del servidor: ${e.code()}"))
            } catch (e: Exception) {
                Log.e("UsuarioRepository", "Error inesperado: ${e.message}")
                Result.failure(e)
            }
        }
    }

    suspend fun register(nombre: String, email: String, password: String): Result<Usuario> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("UsuarioRepository", "Intentando registro para: $email")

                // Crear objeto Usuario para registro
                val usuarioRegistro = Usuario(
                    nombre = nombre,
                    email = email,
                    password = password
                )

                val response = usuarioApiService.register(usuarioRegistro)

                if (response.isSuccessful) {
                    val usuario = response.body()
                    if (usuario != null) {
                        // Guardar token si existe
                        usuario.token?.let { token ->
                            saveToken(token)
                            saveUserEmail(email)
                        }

                        Log.d("UsuarioRepository", "Registro exitoso para: $email")
                        Result.success(usuario)
                    } else {
                        Result.failure(Exception("Error en el registro"))
                    }
                } else {
                    val errorMsg = when (response.code()) {
                        400 -> "Datos inválidos"
                        409 -> "El usuario ya existe"
                        500 -> "Error del servidor"
                        else -> "Error: ${response.code()}"
                    }
                    Log.e("UsuarioRepository", "Error HTTP: ${response.code()}")
                    Result.failure(Exception(errorMsg))
                }
            } catch (e: IOException) {
                Log.e("UsuarioRepository", "Error de conexión: ${e.message}")
                Result.failure(Exception("Error de conexión. Verifica tu internet."))
            } catch (e: HttpException) {
                Log.e("UsuarioRepository", "Error HTTP: ${e.message}")
                Result.failure(Exception("Error del servidor: ${e.code()}"))
            } catch (e: Exception) {
                Log.e("UsuarioRepository", "Error inesperado: ${e.message}")
                Result.failure(e)
            }
        }
    }

    // ------------------------------
    // OBTENER PERFIL
    // ------------------------------
    suspend fun getPerfil(): Result<Usuario> {
        return withContext(Dispatchers.IO) {
            try {
                val token = getToken()
                val email = getUserEmail()

                if (token.isNullOrEmpty() || email.isNullOrEmpty()) {
                    return@withContext Result.failure(Exception("No autenticado"))
                }

                Log.d("UsuarioRepository", "Obteniendo perfil para: $email")

                // Según tu UsuarioApiService, necesitas el email
                val response = usuarioApiService.getUsuarioPorEmail(email)

                if (response.isSuccessful) {
                    val usuario = response.body()
                    if (usuario != null) {
                        Log.d("UsuarioRepository", "Perfil obtenido: ${usuario.nombre}")
                        Result.success(usuario)
                    } else {
                        Result.failure(Exception("Perfil no encontrado"))
                    }
                } else {
                    when (response.code()) {
                        401 -> {
                            // Token inválido o expirado
                            clearToken()
                            Result.failure(Exception("Sesión expirada. Vuelve a iniciar sesión."))
                        }
                        404 -> Result.failure(Exception("Perfil no encontrado"))
                        else -> Result.failure(Exception("Error: ${response.code()}"))
                    }
                }
            } catch (e: IOException) {
                Log.e("UsuarioRepository", "Error de conexión al obtener perfil")
                Result.failure(Exception("Error de conexión"))
            } catch (e: Exception) {
                Log.e("UsuarioRepository", "Error al obtener perfil: ${e.message}")
                Result.failure(e)
            }
        }
    }

    // ------------------------------
    // MANEJO DE TOKEN Y SESIÓN
    // ------------------------------
    private fun saveToken(token: String) {
        sharedPreferences.edit().putString("auth_token", token).apply()
    }

    private fun getToken(): String? {
        return sharedPreferences.getString("auth_token", null)
    }

    private fun saveUserEmail(email: String) {
        sharedPreferences.edit().putString("user_email", email).apply()
    }

    fun getUserEmail(): String? {
        return sharedPreferences.getString("user_email", null)
    }

    private fun clearToken() {
        sharedPreferences.edit().remove("auth_token").apply()
    }

    fun logout() {
        sharedPreferences.edit().clear().apply()
    }

    fun isLoggedIn(): Boolean {
        return !getToken().isNullOrEmpty()
    }
}