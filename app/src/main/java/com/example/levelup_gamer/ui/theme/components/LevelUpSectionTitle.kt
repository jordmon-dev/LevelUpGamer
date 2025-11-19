package com.example.levelup_gamer.ui.theme.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LevelUpSectionTitle(text: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
        Text(
            text.uppercase(),
            color = Color(0xFF00FF88),
            style = MaterialTheme.typography.headlineSmall
        )
    }
    Divider(
        modifier = Modifier.padding(top = 4.dp),
        color = Color(0xFF00FF88),
        thickness = 2.dp
    )
}
