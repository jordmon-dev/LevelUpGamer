package com.example.levelup_gamer.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun GlowIconButton(
    icon: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(52.dp)
            .shadow(12.dp, shape = MaterialTheme.shapes.small)
            .background(Color(0xFF00FF88), MaterialTheme.shapes.small)
    ) {
        Icon(icon, contentDescription, tint = Color.Black)
    }
}
