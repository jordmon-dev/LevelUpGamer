plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.levelup_gamer"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.levelup_gamer"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

    // Configuración para evitar conflictos de archivos duplicados
    packaging {
        resources {
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-apache-2.0.txt"
            excludes += "META-INF/LICENSE"
            excludes += "META-INF/NOTICE.md"
            excludes += "META-INF/NOTICE"
            excludes += "META-INF/ASL2.0"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }
}

dependencies {

    /* ============================ RECURSO NATIVO CÁMARA ===================== */
    implementation("androidx.camera:camera-camera2:1.5.0")
    implementation("androidx.camera:camera-lifecycle:1.5.0")
    implementation("androidx.camera:camera-view:1.5.0")
    implementation("androidx.camera:camera-compose:1.0.0-alpha02")
    implementation("com.google.accompanist:accompanist-permissions:0.34.0")
    implementation("io.coil-kt:coil-compose:2.4.0")

    /* ============================ GEOLOCALIZACIÓN Y MAPAS ===================== */
    implementation("com.google.android.gms:play-services-location:21.3.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.8.0")

    // MapBox
    implementation("com.mapbox.maps:android-ndk27:11.16.2")
    implementation("com.mapbox.extension:maps-compose-ndk27:11.16.2")

    /* ============================ BASE DE DATOS ROOM ===================== */
    val roomVersion = "2.8.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
    // 'annotationProcessor' es para Java, en Kotlin usamos ksp, por eso quité la línea duplicada

    /* ============================ COMPOSE Y ANDROID CORE ===================== */
    implementation(platform(libs.androidx.compose.bom))
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.compose.ui:ui:1.7.3")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.3.0")
    implementation("androidx.navigation:navigation-compose:2.8.0")
    implementation("androidx.core:core-ktx:1.12.0")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.6")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")

    /* ============================ DATASTORE Y SEGURIDAD ===================== */
    implementation("androidx.security:security-crypto:1.1.0-alpha06")
    implementation("androidx.datastore:datastore-preferences:1.1.7")
    implementation("androidx.compose.material:material-icons-extended:1.7.8")

    /* ============================ RETROFIT (API) ===================== */
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Coroutines Android
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    /* ============================ PRUEBAS (TESTING) ===================== */

    // Unit Tests (Carpeta test)
    testImplementation(libs.junit)
    testImplementation("io.mockk:mockk:1.13.8") // MockK Clásico

    // Android Tests (Carpeta androidTest - Emulador)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform(libs.androidx.compose.bom))

    // Importante: Versión específica para UI Tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation("io.mockk:mockk-android:1.13.8") // MockK Android

    // Debug (Necesario para tests de UI)
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}