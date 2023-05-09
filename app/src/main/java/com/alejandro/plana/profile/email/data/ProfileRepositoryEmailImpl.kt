package com.alejandro.plana.profile.email.data

import com.alejandro.plana.inicio.email.domain.model.ResponseEmail.*
import com.alejandro.plana.profile.email.domain.ProfilerepositoryEmail
import com.alejandro.plana.profile.email.domain.RevokeAccessResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepositoryEmailImpl @Inject constructor(
    private val auth: FirebaseAuth
) : ProfilerepositoryEmail {
    override fun signOut() = auth.signOut()

    override suspend fun revokeAccess(): RevokeAccessResponse {
        return try {
            auth.currentUser?.delete()?.await()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }
}