package com.alejandro.plana.login.ui.forgot_password

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandro.plana.inicio.email.domain.model.ResponseEmail.*
import com.alejandro.plana.inicio.email.domain.repository.AuthRepositoryEmail
import com.alejandro.plana.inicio.email.domain.repository.SendPasswordResetEmailResponse
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val repo: AuthRepositoryEmail
): ViewModel() {
    var sendPasswordResetEmailResponse by mutableStateOf<SendPasswordResetEmailResponse>(
        Success(false)
    )

    fun sendPasswordResetEmail(email: String) = viewModelScope.launch {
        sendPasswordResetEmailResponse = Loading
        sendPasswordResetEmailResponse = repo.sendPasswordResetEmail(email)
    }
}