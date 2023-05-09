package com.alejandro.plana.login.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.alejandro.plana.components.ProgressBar
import com.alejandro.plana.inicio.email.domain.model.ResponseEmail.*
import com.alejandro.plana.login.ui.LoginViewModel


@Composable
fun LogIn(
    viewModel: LoginViewModel = hiltViewModel(),
    showErrorMessage: (errorMessage: String?) -> Unit
) {
    when(val signInResponse = viewModel.signInResponse) {
        is Loading -> ProgressBar()
        is Success -> Unit
        is Failure -> signInResponse.apply {
            LaunchedEffect(e) {
                print(e)
                showErrorMessage(e.message)
            }
        }
    }
}