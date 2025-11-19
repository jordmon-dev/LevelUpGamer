package com.example.levelup_gamer.ui.theme.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LevelUpHeader(title: String, onBack: (() -> Unit)? = null) {
    val fondo = Brush.verticalGradient(
        listOf(
            Color(0xFF0A0A0A),
            Color(0xFF1A1A2E),
            Color(0xFF16213E)
        )
    )

    CenterAlignedTopAppBar(
        title = { Text(title, color = Color.White) },
        navigationIcon = {
            if (onBack != null) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.White)
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent
        )
    )
}
