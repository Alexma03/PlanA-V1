package com.alejandro.plana.login.ui.forgot_password

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.alejandro.plana.core.Utils.Companion.showMessage
import com.alejandro.plana.login.ui.forgot_password.components.ForgotPassword
import com.alejandro.plana.login.ui.forgot_password.components.ForgotPasswordContent
import com.alejandro.plana.login.ui.forgot_password.components.ForgotPasswordTopBar

@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            ForgotPasswordTopBar(
                navigateBack = navigateBack
            )
        },
        content = { padding ->
            ForgotPasswordContent(
                padding = padding,
                sendPasswordResetEmail = { email ->
                    viewModel.sendPasswordResetEmail(email)
                }
            )
        }
    )

    ForgotPassword(
        navigateBack = navigateBack,
        showResetPasswordMessage = {
            showMessage(context, "We've sent you an email with a link to reset the password.")
        },
        showErrorMessage = { errorMessage ->
            showMessage(context, errorMessage)
        }
    )
}