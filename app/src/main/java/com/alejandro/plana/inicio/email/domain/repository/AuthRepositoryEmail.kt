package com.alejandro.plana.inicio.email.domain.repository

import com.alejandro.plana.inicio.email.domain.model.ResponseEmail
import com.google.firebase.auth.FirebaseUser
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

typealias SignUpResponse = ResponseEmail<Boolean>
typealias SendEmailVerificationResponse = ResponseEmail<Boolean>
typealias SignInResponse = ResponseEmail<Boolean>
typealias SendPasswordResetEmailResponse = ResponseEmail<Boolean>
typealias AuthStateResponse = StateFlow<Boolean>

interface AuthRepositoryEmail {
    val currentUser: FirebaseUser?

    suspend fun firebaseSignUpWithEmailAndPassword(email: String, password: String): SignUpResponse

    suspend fun sendEmailVerification(): SendEmailVerificationResponse

    suspend fun firebaseSignInWithEmailAndPassword(email: String, password: String): SignInResponse

    suspend fun sendPasswordResetEmail(email: String): SendPasswordResetEmailResponse

    fun getAuthState(viewModelScope: CoroutineScope): AuthStateResponse
}