package com.example.levelup_gamer.Screen


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.levelup_gamer.viewmodel.AppScreens


@Composable

fun Home(navController: NavController){
    Column {
        Text("Bienvenido a")
        Spacer(modifier = Modifier.height(10.dp))
        Text("Level-Up-Gamer")
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {

                navController.navigate(AppScreens.AboutScreen.route)

            }
        ) {
            Text("Acerca de")
        }
    }
}