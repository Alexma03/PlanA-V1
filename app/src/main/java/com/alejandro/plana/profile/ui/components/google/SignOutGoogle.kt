package com.alejandro.plana.profile.ui.components.google

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.alejandro.plana.inicio.google.domain.model.ResponseGoogle.*
import com.alejandro.plana.inicio.ui.components.ProgressBar
import com.alejandro.plana.profile.ui.ProfileViewModel

@Composable
fun SignOutGoogle(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToAuthScreen: (signedOut: Boolean) -> Unit
) {
    when(val signOutResponse = viewModel.signOutResponseGoogle) {
        is Loading -> ProgressBar()
        is Success -> signOutResponse.data?.let { signedOut ->
            LaunchedEffect(signedOut) {
                navigateToAuthScreen(signedOut)
            }
        }
        is Failure -> LaunchedEffect(Unit) {
            print(signOutResponse.e)
        }
    }
}