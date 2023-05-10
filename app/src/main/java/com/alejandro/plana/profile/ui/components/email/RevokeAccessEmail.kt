package com.alejandro.plana.profile.ui.components.email

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.alejandro.plana.components.ProgressBar
import com.alejandro.plana.core.Constants.REVOKE_ACCESS_MESSAGE
import com.alejandro.plana.core.Constants.SENSITIVE_OPERATION_MESSAGE
import com.alejandro.plana.core.Constants.SIGN_OUT
import com.alejandro.plana.core.Utils.Companion.showMessage
import com.alejandro.plana.inicio.email.domain.model.ResponseEmail.*
import com.alejandro.plana.profile.ui.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun RevokeAccessEmail(
    viewModel: ProfileViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope,
    signOut: () -> Unit,
) {
    val context = LocalContext.current

    fun showRevokeAccessMessage() = coroutineScope.launch {
        val result = scaffoldState.snackbarHostState.showSnackbar(
            message = REVOKE_ACCESS_MESSAGE,
            actionLabel = SIGN_OUT
        )
        if (result == SnackbarResult.ActionPerformed) {
            signOut()
        }
    }

    when(val revokeAccessResponse = viewModel.revokeAccessResponseEmail) {
        is Loading -> ProgressBar()
        is Success -> {
            val isAccessRevoked = revokeAccessResponse.data
            LaunchedEffect(isAccessRevoked) {
                if (isAccessRevoked) {
                    showMessage(context, REVOKE_ACCESS_MESSAGE)
                }
            }
        }
        is Failure -> revokeAccessResponse.apply {
            LaunchedEffect(e) {
                print(e)
                if (e.message == SENSITIVE_OPERATION_MESSAGE) {
                    showRevokeAccessMessage()
                }
            }
        }
    }
}