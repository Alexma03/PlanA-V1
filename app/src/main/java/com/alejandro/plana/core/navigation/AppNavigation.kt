package com.alejandro.plana.core.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.alejandro.plana.core.navigation.Routes.*
import com.alejandro.plana.home.ui.HomeScreen
import com.alejandro.plana.inicio.ui.InicioScreen
import com.alejandro.plana.login.ui.LoginScreen
import com.alejandro.plana.login.ui.forgot_password.ForgotPasswordScreen
import com.alejandro.plana.login.ui.forgot_password.components.ForgotPassword
import com.alejandro.plana.profile.ui.ProfileScreen
import com.alejandro.plana.resgistro.ui.RegistroScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable


@Composable
@ExperimentalAnimationApi
fun Navegacion(
    navController: NavHostController
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Inicio.route,
        enterTransition = {EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(
            route = Inicio.route
        ) {
            InicioScreen(
                navController,
                navigateToProfileScreen = {
                    navController.navigate(Profile.route)
                }
            )
        }
        composable(
            route = Profile.route
        ) {
            ProfileScreen(
                navigateToAuthScreen = {
                    navController.popBackStack()
                    navController.navigate(Inicio.route)
                }
            )
        }
        composable(route = Home.route) {
            HomeScreen(navigationController = navController)
        }
        composable(route = Registro.route) {
            RegistroScreen(navController = navController)
        }
        composable(route = Login.route) {
            LoginScreen(navController = navController)
        }
        composable(route = ForgotPassword.route) {
            ForgotPasswordScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(route = VerifyEmail.route) {
            ForgotPasswordScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}

