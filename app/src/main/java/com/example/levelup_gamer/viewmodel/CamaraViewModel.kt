// CameraViewModel.kt
package com.example.levelup_gamer.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor

class CameraViewModel : ViewModel() {
    var imageUri by mutableStateOf<Uri?>(null)
    var showCamera by mutableStateOf(false)
    var cameraError by mutableStateOf<String?>(null)
    var isCameraInitialized by mutableStateOf(false)

    private var imageCapture: ImageCapture? = null
    private var cameraProvider: ProcessCameraProvider? = null

    fun startCamera(context: Context, previewView: PreviewView, executor: Executor) {
        Log.d("CameraViewModel", "Iniciando cámara...")
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            try {
                cameraProvider = cameraProviderFuture.get()
                val provider = this.cameraProvider ?: run {
                    cameraError = "No se pudo obtener el proveedor de cámara"
                    return@addListener
                }

                // Configurar Preview
                val preview = Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                // Configurar ImageCapture
                imageCapture = ImageCapture.Builder()
                    .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                    .build()

                // Seleccionar cámara trasera
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                // Verificar que hay una cámara trasera disponible
                if (!provider.hasCamera(cameraSelector)) {
                    cameraError = "Cámara trasera no disponible"
                    return@addListener
                }

                // Unbind cualquier use case previo y bind nuevos
                provider.unbindAll()
                provider.bindToLifecycle(
                    context as androidx.lifecycle.LifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture
                )

                isCameraInitialized = true
                cameraError = null
                Log.d("CameraViewModel", "Cámara iniciada correctamente")

            } catch (exc: Exception) {
                cameraError = "Error al iniciar cámara: ${exc.message}"
                Log.e("CameraViewModel", "Error iniciando cámara", exc)
                isCameraInitialized = false
            }
        }, executor)
    }

    fun takePhoto(context: Context, executor: Executor, onPhotoTaken: (Uri) -> Unit) {
        val imageCapture = imageCapture ?: run {
            cameraError = "Cámara no inicializada"
            return
        }

        if (!isCameraInitialized) {
            cameraError = "Cámara no está lista"
            return
        }

        // Crear directorio si no existe
        val storageDir = context.externalCacheDir ?: context.cacheDir
        val photoFile = File(
            storageDir,
            "profile_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())}.jpg"
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        Log.d("CameraViewModel", "Tomando foto...")
        imageCapture.takePicture(
            outputOptions,
            executor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    viewModelScope.launch {
                        val uri = output.savedUri ?: Uri.fromFile(photoFile)
                        imageUri = uri
                        Log.d("CameraViewModel", "Foto guardada: $uri")
                        onPhotoTaken(uri)
                        showCamera = false
                    }
                }

                override fun onError(exc: ImageCaptureException) {
                    cameraError = "Error al tomar foto: ${exc.message}"
                    Log.e("CameraViewModel", "Error tomando foto", exc)
                }
            }
        )
    }

    fun openCamera() {
        Log.d("CameraViewModel", "Abriendo cámara")
        showCamera = true
        imageUri = null
        cameraError = null
        isCameraInitialized = false
    }

    fun closeCamera() {
        Log.d("CameraViewModel", "Cerrando cámara")
        showCamera = false
        cameraProvider?.unbindAll()
        cameraProvider = null
        isCameraInitialized = false
    }

    override fun onCleared() {
        super.onCleared()
        closeCamera()
    }
}
