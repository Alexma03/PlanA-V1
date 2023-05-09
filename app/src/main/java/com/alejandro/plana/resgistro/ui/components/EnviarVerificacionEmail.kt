package com.alejandro.plana.resgistro.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.alejandro.plana.inicio.email.domain.model.ResponseEmail
import com.alejandro.plana.inicio.email.domain.model.ResponseEmail.*
import com.alejandro.plana.inicio.ui.components.ProgressBar
import com.alejandro.plana.resgistro.ui.RegistroViewModel

@Composable
fun EnviarVerificacionEmail(
    viewModel: RegistroViewModel = hiltViewModel()
) {
    when(val sendEmailVerificationResponse = viewModel.sendEmailVerificationResponse) {
        is Loading -> ProgressBar()
        is Success<*> -> Unit
        is Failure -> sendEmailVerificationResponse.apply {
            LaunchedEffect(e) {
                print(e)
            }
        }
    }
}