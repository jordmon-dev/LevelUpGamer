package com.example.levelup_gamer;

import com.example.levelup_gamer.modelo.ApiResponse
import com.example.levelup_gamer.modelo.Usuario
import com.example.levelup_gamer.remote.UsuarioService
import com.example.levelup_gamer.repository.UsuarioRepository
import io.mockk.coEvery;
import io.mockk.mockk;
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Response

class UsuarioRepositoryTest {

    private val apiService: UsuarioService = UsuarioInstance.api // Valor por defecto para que no rompa el resto de la app

    // 1. Mockeamos tu servicio (simulamos la API)
    private val mockApiService = mockk<UsuarioService>()

    // 2. Creamos el repositorio inyectándole el servicio falso
    // NOTA: Asegúrate de modificar tu UsuarioRepository para aceptar el servicio en el constructor
    // ej: class UsuarioRepository(private val api: UsuarioService = RetrofitInstance.api)
    private val repository = UsuarioRepository(mockApiService)

    @Test
    fun getPerfil_retorna_usuario_exitosamente() = runTest {

        // GIVEN: Preparamos los datos de prueba (igual que el profesor hizo con PostPrueba)
        val usuarioPrueba = Usuario(
                nombre = "Jordmon",
                email = "test@correo.com",
                password = "123"
        )
        val respuestaApi = ApiResponse(
                data = usuarioPrueba,
                message = "Exito"
        )
        val mockResponse = Response.success(respuestaApi)

        // WHEN: Entrenamos al mock para que devuelva esto cuando llamen a getPerfil
        coEvery { mockApiService.getPerfil("Bearer token123") } returns mockResponse

        // THEN: Ejecutamos la función real del repositorio
        val resultado = repository.getPerfil("Bearer token123")

        // Verificamos que sea exitoso y que el nombre sea el esperado
        assertTrue(resultado.isSuccessful)
        assertEquals("Jordmon", resultado.body()?.data?.nombre)
    }
}
