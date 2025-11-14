App Android nativa para un e-commerce gamer. Implementada con Kotlin, Jetpack Compose y arquitectura MVVM como proyecto académico. 

# 🎮 Level-Up Gamer

**Level-Up Gamer** es una aplicación móvil desarrollada en **Kotlin + Jetpack Compose** que simula una tienda virtual de productos gamer.  
Incluye un catálogo con imágenes, carrito de compras, login, registro, perfil, cámara, GPS y navegación completa usando arquitectura MVVM.

Proyecto desarrollado para la asignatura **Desarrollo de Aplicaciones Móviles – Duoc UC (2025)**.

----------------------------------------------------------------------------------------------------------------------------------

Integrantes

Jordy Mondaca, 
Elias Vicencio
y Andres Yañez
Carrera: Analista Programador
----------------------------------------------------------------------------------------------------------------------------------


## 📌 Funcionalidades implementadas

### 🧩 Funcionalidades generales
- Pantalla de **Login**
- Pantalla de **Registro**
- Pantalla **Home** con navegación
- **Catálogo de productos** con imágenes
- **Carrito de compras** (añadir y quitar productos)
- **Perfil del usuario**
- Pantalla de **Acerca de**
- Pantalla de **Ayuda**
- **Notificaciones internas**
- **Simulación de pago** y pantalla de confirmación

### 🧭 Navegación
- Implementada con **Navigation Compose**
- Más de **10 pantallas**

### 🧠 Arquitectura MVVM
- Uso de **ViewModel**, **StateFlow** y estados reactivos
- Archivos incluidos en: `/model` y `/viewmodel`

### 📸 Cámara (Recurso nativo)
- Uso de la cámara del dispositivo
- Guardado de la foto con **FileProvider**
- Vista previa de la imagen dentro de la app

### 🧭 GPS (Recurso nativo)
- Obtiene la ubicación actual del usuario
- Muestra un mapa mediante **Mapbox Compose**
- Solicitud correcta de permisos de ubicación

---

## ▶️ Pasos para ejecutar el proyecto

1. Clonar el repositorio desde GitHub:
   ```bash
   git clone https://github.com/usuario/LevelUpGamer.git
   
O clonar directamente desde AndroidStudio 

Abrir el proyecto en Android Studio.

Esperar que Gradle sincronice automáticamente.
Si es necesario, pulsar File → Sync Project with Gradle Files.

Asegurarse de tener configurado:

SDK 34 o superior

Dependencias de Mapbox funcionando

Un dispositivo o emulador con Google Play Services

Ejecutar la app con:

Botón Run (▶)

O desde el menú: Run → Run 'app'

Aceptar los permisos solicitados por la app:

Cámara

Ubicación
