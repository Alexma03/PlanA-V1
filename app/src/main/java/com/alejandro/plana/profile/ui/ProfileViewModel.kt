package com.alejandro.plana.profile.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandro.plana.inicio.email.domain.model.ResponseEmail
import com.alejandro.plana.inicio.google.domain.model.ResponseGoogle
import com.alejandro.plana.inicio.google.domain.model.ResponseGoogle.*
import com.alejandro.plana.profile.email.domain.ProfilerepositoryEmail
import com.alejandro.plana.profile.email.domain.ReloadUserResponseEmail
import com.alejandro.plana.profile.email.domain.RevokeAccessResponseEmail
import com.alejandro.plana.profile.google.domain.ProfileRepositoryGoogle
import com.alejandro.plana.profile.google.domain.RevokeAccessResponseGoogle
import com.alejandro.plana.profile.google.domain.SignOutResponseGoogle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repoGoogle: ProfileRepositoryGoogle, private val repoEmail: ProfilerepositoryEmail) :
    ViewModel() {
    val isEmailVerified get() = repoEmail.currentUser?.isEmailVerified ?: false
    val displayName get() = repoGoogle.displayName
    val photoUrl get() = repoGoogle.photoUrl

    var signOutResponseGoogle by mutableStateOf<SignOutResponseGoogle>(Success(false))
        private set
    var revokeAccessResponseGoogle by mutableStateOf<RevokeAccessResponseGoogle>(Success(false))
        private set
    var reloadUserResponseEmail by mutableStateOf<ReloadUserResponseEmail>(ResponseEmail.Success(false))
        private set
    var revokeAccessResponseEmail by mutableStateOf<RevokeAccessResponseEmail>(ResponseEmail.Success(false))
        private set

    fun reloadUserEmail() = viewModelScope.launch {
        reloadUserResponseEmail = ResponseEmail.Loading
        reloadUserResponseEmail = repoEmail.reloadFirebaseUser()
    }
    fun signOutEmail() = repoEmail.signOutEmail()
    fun revokeAccessEmail() = viewModelScope.launch {
        revokeAccessResponseEmail = ResponseEmail.Loading
        revokeAccessResponseEmail = repoEmail.revokeAccessEmail()
    }
    fun signOutGoogle() = viewModelScope.launch {
        signOutResponseGoogle = ResponseGoogle.Loading
        signOutResponseGoogle = repoGoogle.signOut()
    }
    fun revokeAccessGoogle() = viewModelScope.launch {
        revokeAccessResponseGoogle = ResponseGoogle.Loading
        revokeAccessResponseGoogle = repoGoogle.revokeAccess()
    }
}
