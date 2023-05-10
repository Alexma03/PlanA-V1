package com.alejandro.plana.profile.google.domain

import com.alejandro.plana.inicio.google.domain.model.ResponseGoogle

typealias SignOutResponseGoogle = ResponseGoogle<Boolean>
typealias RevokeAccessResponseGoogle = ResponseGoogle<Boolean>

interface ProfileRepositoryGoogle {
    val displayName: String
    val photoUrl: String

    suspend fun signOut(): SignOutResponseGoogle

    suspend fun revokeAccess(): RevokeAccessResponseGoogle
}