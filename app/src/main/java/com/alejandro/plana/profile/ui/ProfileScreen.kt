package com.alejandro.plana.profile.ui

import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import com.alejandro.plana.core.Constants.PROFILE_SCREEN
import com.alejandro.plana.core.Constants.REVOKE_ACCESS_MESSAGE
import com.alejandro.plana.core.Constants.SIGN_OUT
import com.alejandro.plana.profile.ui.components.email.ProfileContentEmail
import com.alejandro.plana.profile.ui.components.email.RevokeAccessEmail
import com.alejandro.plana.profile.ui.components.email.TopBar
import com.alejandro.plana.profile.ui.components.google.ProfileContentGoogle
import com.alejandro.plana.profile.ui.components.google.ProfileTopBarGoogle
import com.alejandro.plana.profile.ui.components.google.RevokeAccessGoogle
import com.alejandro.plana.profile.ui.components.google.SignOutGoogle
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToAuthScreen: () -> Unit

) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val user = FirebaseAuth.getInstance().currentUser
    val provider = user?.providerData?.get(1)?.providerId

    if (provider == GoogleAuthProvider.PROVIDER_ID) {
        Scaffold(
            topBar = {
                ProfileTopBarGoogle(
                    signOut = {
                        viewModel.signOutGoogle()
                    },
                    revokeAccess = {
                        viewModel.revokeAccessGoogle()
                    }
                )
            },
            content = { padding ->
                ProfileContentGoogle(
                    padding = padding,
                    photoUrl = viewModel.photoUrl,
                    displayName = viewModel.displayName
                )
            },
            scaffoldState = scaffoldState
        )

        SignOutGoogle(
            navigateToAuthScreen = { signedOut ->
                if (signedOut) {
                    navigateToAuthScreen()
                }
            }
        )

        fun showSnackBar() = coroutineScope.launch {
            val result = scaffoldState.snackbarHostState.showSnackbar(
                message = REVOKE_ACCESS_MESSAGE,
                actionLabel = SIGN_OUT
            )
            if (result == SnackbarResult.ActionPerformed) {
                viewModel.signOutGoogle()
            }
        }

        RevokeAccessGoogle(
            navigateToAuthScreen = { accessRevoked ->
                if (accessRevoked) {
                    navigateToAuthScreen()
                }
            },
            showSnackBar = {
                showSnackBar()
            }
        )
    } else if (provider == EmailAuthProvider.PROVIDER_ID) {
        val scaffoldState = rememberScaffoldState()
        val coroutineScope = rememberCoroutineScope()

        Scaffold(
            topBar = {
                TopBar(
                    title = PROFILE_SCREEN,
                    signOut = {
                        viewModel.signOutEmail()
                    },
                    revokeAccess = {
                        viewModel.revokeAccessEmail()
                    }
                )
            },
            content = { padding ->
                ProfileContentEmail(
                    padding = padding
                )
            },
            scaffoldState = scaffoldState
        )

        RevokeAccessEmail(
            scaffoldState = scaffoldState,
            coroutineScope = coroutineScope,
            signOut = {
                viewModel.signOutEmail()
            }
        )
    }


}
