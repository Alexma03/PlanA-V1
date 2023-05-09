package com.alejandro.plana.inicio.google.domain.repository

import com.alejandro.plana.inicio.domain.model.ResponseGoogle
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.AuthCredential


typealias OneTapSignInResponse = ResponseGoogle<BeginSignInResult>
typealias SignInWithGoogleResponse = ResponseGoogle<Boolean>


interface AuthGoogleRepository {

    val isUserAuthenticatedInFirebase: Boolean

    suspend fun oneTapSignInWithGoogle(): OneTapSignInResponse

    suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential): SignInWithGoogleResponse
}