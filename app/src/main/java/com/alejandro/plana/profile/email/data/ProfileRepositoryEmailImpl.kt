package com.alejandro.plana.profile.email.data

import com.alejandro.plana.inicio.email.domain.model.ResponseEmail.*
import com.alejandro.plana.profile.email.domain.ProfilerepositoryEmail
import com.alejandro.plana.profile.email.domain.ReloadUserResponseEmail
import com.alejandro.plana.profile.email.domain.RevokeAccessResponseEmail
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepositoryEmailImpl @Inject constructor(
    private val auth: FirebaseAuth
) : ProfilerepositoryEmail {
    override val currentUser get() = auth.currentUser
    override fun signOutEmail() = auth.signOut()
    override suspend fun reloadFirebaseUser(): ReloadUserResponseEmail {
        return try {
            auth.currentUser?.reload()?.await()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun revokeAccessEmail(): RevokeAccessResponseEmail {
        return try {
            auth.currentUser?.delete()?.await()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }
}