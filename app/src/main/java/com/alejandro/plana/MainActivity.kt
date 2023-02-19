package com.alejandro.plana

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.alejandro.plana.core.navigation.Navegacion
import com.alejandro.plana.core.navigation.Routes.*
import com.alejandro.plana.inicio.ui.InicioViewModel
import com.alejandro.plana.ui.theme.PlanATheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val viewModel by viewModels<InicioViewModel>()

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.apply {
            // Establece el color de la barra de estado en transparente.
            statusBarColor = Color.Black.value.toInt()
        }
        setContent {
            PlanATheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    navController = rememberAnimatedNavController()
                    Navegacion(
                        navController = navController
                    )
                    checkAuthState()
                }
            }
        }
    }

    private fun checkAuthState() {
        if(viewModel.isUserAuthenticated) {
            navigateToProfileScreen()
        }
    }

    private fun navigateToProfileScreen() = navController.navigate(Profile.route)
}

