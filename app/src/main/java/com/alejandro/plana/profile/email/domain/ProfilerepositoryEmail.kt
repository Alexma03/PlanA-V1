package com.alejandro.plana.profile.email.domain

import com.alejandro.plana.inicio.email.domain.model.ResponseEmail
import kotlinx.coroutines.flow.StateFlow

typealias RevokeAccessResponse = ResponseEmail<Boolean>

interface ProfilerepositoryEmail {
    fun signOut()

    suspend fun revokeAccess(): RevokeAccessResponse
}