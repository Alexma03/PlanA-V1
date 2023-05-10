package com.alejandro.plana.core.navigation

import com.alejandro.plana.core.Constants.FORGOT_PASSWORD_SCREEN
import com.alejandro.plana.core.Constants.HOME_SCREEN
import com.alejandro.plana.core.Constants.INICIO_SCREEN
import com.alejandro.plana.core.Constants.LOGIN_SCREEN
import com.alejandro.plana.core.Constants.PROFILE_SCREEN
import com.alejandro.plana.core.Constants.REGISTRO_SCREEN
import com.alejandro.plana.core.Constants.VERIFY_EMAIL_SCREEN

sealed class Routes(val route: String) {
    object Inicio: Routes(INICIO_SCREEN)
    object Registro: Routes(REGISTRO_SCREEN)
    object Home: Routes(HOME_SCREEN)
    object Login: Routes(LOGIN_SCREEN)
    object Profile: Routes(PROFILE_SCREEN)
    object ForgotPassword: Routes(FORGOT_PASSWORD_SCREEN)
    object VerifyEmail: Routes(VERIFY_EMAIL_SCREEN)


}