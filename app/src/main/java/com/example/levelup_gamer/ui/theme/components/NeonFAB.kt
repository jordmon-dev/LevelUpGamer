package com.example.levelup_gamer.ui.theme.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun NeonFAB(onClick: () -> Unit, icon: ImageVector) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = Color(0xFF00FF88),
        contentColor = Color.Black,
        elevation = FloatingActionButtonDefaults.elevation(8.dp)
    ) {
        Icon(icon, contentDescription = null)
    }
}
