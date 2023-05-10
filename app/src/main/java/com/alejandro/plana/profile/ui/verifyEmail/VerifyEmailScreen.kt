package com.alejandro.plana.profile.ui.verifyEmail

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.alejandro.plana.core.Utils.Companion.showMessage
import com.alejandro.plana.profile.ui.ProfileViewModel
import com.alejandro.plana.profile.ui.components.email.RevokeAccessEmail
import com.alejandro.plana.profile.ui.components.email.TopBar
import com.alejandro.plana.profile.ui.verifyEmail.components.ReloadUser
import com.alejandro.plana.profile.ui.verifyEmail.components.VerifyEmailContent


@Composable
fun VerifyEmailScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToProfileScreen: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopBar(
                title = "Verify email",
                signOut = {
                    viewModel.signOutEmail()
                },
                revokeAccess = {
                    viewModel.revokeAccessEmail()
                }
            )
        },
        content = { padding ->
            VerifyEmailContent(
                padding = padding,
                reloadUser = {
                    viewModel.reloadUserEmail()
                }
            )
        },
        scaffoldState = scaffoldState
    )

    ReloadUser(
        navigateToProfileScreen = {
            if (viewModel.isEmailVerified) {
                navigateToProfileScreen()
            } else {
                showMessage(context, "Your email is not verified.")
            }
        }
    )

    RevokeAccessEmail(
        scaffoldState = scaffoldState,
        coroutineScope = coroutineScope,
        signOut = {
            viewModel.signOutEmail()
        }
    )
}