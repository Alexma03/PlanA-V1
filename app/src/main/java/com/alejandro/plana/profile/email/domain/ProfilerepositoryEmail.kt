package com.alejandro.plana.profile.email.domain

import com.alejandro.plana.inicio.email.domain.model.ResponseEmail
import com.google.firebase.auth.FirebaseUser

typealias RevokeAccessResponseEmail = ResponseEmail<Boolean>
typealias ReloadUserResponseEmail = ResponseEmail<Boolean>

interface ProfilerepositoryEmail {
    val currentUser: FirebaseUser?
    fun signOutEmail()
    suspend fun reloadFirebaseUser(): ReloadUserResponseEmail
    suspend fun revokeAccessEmail(): RevokeAccessResponseEmail
}