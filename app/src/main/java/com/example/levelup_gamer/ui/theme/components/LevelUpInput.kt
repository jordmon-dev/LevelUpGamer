package com.example.levelup_gamer.ui.theme.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun LevelUpInput(
    value: String,
    onChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        label = { Text(label) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color(0xFF00FF88),
            unfocusedIndicatorColor = Color(0xFF666666),
            focusedLabelColor = Color(0xFF00FF88),
            cursorColor = Color(0xFF00FF88),
            unfocusedLabelColor = Color.White
        )
    )
}
