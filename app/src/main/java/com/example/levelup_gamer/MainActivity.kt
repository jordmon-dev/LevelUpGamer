package com.example.levelup_gamer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.levelup_gamer.navegate.AppNavigate
import com.example.levelup_gamer.ui.theme.theme.LevelUpGamerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LevelUpGamerTheme {
                // AppNavigate NO requiere parámetros
                AppNavigate()
            }
        }
    }
}
