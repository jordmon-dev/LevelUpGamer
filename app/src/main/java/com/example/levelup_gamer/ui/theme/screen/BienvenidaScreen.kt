package com.example.levelup_gamer.ui.theme.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.levelup_gamer.viewmodel.UsuarioViewModel

@Composable
fun BienvenidaScreen(viewModel: UsuarioViewModel){

    val usuario by viewModel.usuario.collectAsState();

    Column(modifier = Modifier.padding(20.dp)) {
        Text("Bienvenido",
            style = MaterialTheme.typography.headlineLarge)
        Text("Holi ${usuario.nombre}",
            style = MaterialTheme.typography.bodyMedium)
    }
}