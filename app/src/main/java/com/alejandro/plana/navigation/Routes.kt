package com.alejandro.plana.navigation

sealed class Routes(val route: String) {
    object Inicio: Routes("inicio")
    object Registro: Routes("registro")
    object Home: Routes("home")
    object Login: Routes("login")



}