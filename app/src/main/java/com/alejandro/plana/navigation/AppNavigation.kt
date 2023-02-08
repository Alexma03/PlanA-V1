package com.alejandro.plana

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alejandro.plana.resgistro.ui.RegistroScreen
import com.alejandro.plana.resgistro.ui.RegistroViewModel
import com.alejandro.plana.navigation.Routes

@Composable
fun Navegacion() {
    val navigationController = rememberNavController()
    NavHost(navController = navigationController, startDestination = Routes.Registro.route) {
        composable(Routes.Inicio.route) {  }
        composable(Routes.Registro.route) { RegistroScreen(RegistroViewModel(), navigationController) }
        composable(Routes.Home.route) {  }
        composable(Routes.Login.route) {  }

    }

}