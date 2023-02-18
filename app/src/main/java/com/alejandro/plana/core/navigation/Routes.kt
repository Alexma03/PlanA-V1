package com.alejandro.plana.core.navigation

sealed class Routes(val route: String) {
    object Inicio: Routes("inicio")
    object Registro: Routes("registro")
    object Home: Routes("home")
    object Login: Routes("login")
    object Prueba: Routes("prueba")


}