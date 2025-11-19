package com.example.levelup_gamer.ui.theme.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun LevelUpChip(
    selected: Boolean,
    text: String,
    onClick: () -> Unit
) {
    AssistChip(
        onClick = onClick,
        label = {
            Text(text, color = if (selected) Color.Black else Color.White)
        },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = if (selected) Color(0xFF00FF88) else Color(0xFF1F1F2E)
        )
    )
}
