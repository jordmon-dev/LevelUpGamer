package com.example.levelup_gamer.ui.theme.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LevelUpOutlinedButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Color(0xFF00FF88)   // Texto e ícono en verde neon
        ),
        border = BorderStroke(
            width = 2.dp,
            color = Color(0xFF00FF88)         // Borde neon
        )
    ) {
        Text(text)
    }
}
