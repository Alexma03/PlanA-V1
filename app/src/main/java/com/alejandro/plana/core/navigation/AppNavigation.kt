package com.alejandro.plana.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alejandro.plana.inicio.ui.InicioScreen
import com.alejandro.plana.resgistro.ui.RegistroScreen
import com.alejandro.plana.resgistro.ui.RegistroViewModel
import com.alejandro.plana.login.ui.LoginScreen
import com.alejandro.plana.login.ui.LoginViewModel

@Composable
fun Navegacion() {
    val navigationController = rememberNavController()
    NavHost(navController = navigationController, startDestination = Routes.Inicio.route) {
        composable(Routes.Inicio.route) { InicioScreen(navigationController) }
        composable(Routes.Registro.route) { RegistroScreen(RegistroViewModel(), navigationController) }
        composable(Routes.Home.route) {  }
        composable(Routes.Login.route) { LoginScreen(navigationController, LoginViewModel()) }
        composable(Routes.Prueba.route) {  }
    }

}