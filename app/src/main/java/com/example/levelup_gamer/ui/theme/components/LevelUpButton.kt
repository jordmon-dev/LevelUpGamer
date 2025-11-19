package com.example.levelup_gamer.ui.theme.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LevelUpButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.shadow(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF00FF88),
            contentColor = Color.Black
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(text, style = MaterialTheme.typography.bodyLarge)
    }
}
