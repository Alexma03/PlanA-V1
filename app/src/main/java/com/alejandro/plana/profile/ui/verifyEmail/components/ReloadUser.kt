package com.alejandro.plana.profile.ui.verifyEmail.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.alejandro.plana.components.ProgressBar
import com.alejandro.plana.inicio.email.domain.model.ResponseEmail
import com.alejandro.plana.inicio.email.domain.model.ResponseEmail.*
import com.alejandro.plana.profile.ui.ProfileViewModel

@Composable
fun ReloadUser(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToProfileScreen: () -> Unit
) {
    when(val reloadUserResponse = viewModel.reloadUserResponseEmail) {
        is Loading -> ProgressBar()
        is Success -> {
            val isUserReloaded = reloadUserResponse.data
            LaunchedEffect(isUserReloaded) {
                if (isUserReloaded) {
                    navigateToProfileScreen()
                }
            }
        }
        is Failure -> reloadUserResponse.apply {
            LaunchedEffect(e) {
                print(e)
            }
        }
    }
}