package com.alejandro.plana.inicio.domain.repository

import com.alejandro.plana.inicio.domain.model.ResponseGoogle

typealias SignOutResponse = ResponseGoogle<Boolean>
typealias RevokeAccessResponse = ResponseGoogle<Boolean>

interface ProfileGoogleRepository {
    val displayName: String
    val photoUrl: String

    suspend fun signOut(): SignOutResponse

    suspend fun revokeAccess(): RevokeAccessResponse
}