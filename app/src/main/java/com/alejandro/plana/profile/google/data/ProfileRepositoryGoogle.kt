package com.alejandro.plana.profile.google.data

import com.alejandro.plana.core.Constants.USERS
import com.alejandro.plana.inicio.google.domain.model.ResponseGoogle.*
import com.alejandro.plana.profile.google.domain.ProfileGoogleRepository
import com.alejandro.plana.profile.google.domain.RevokeAccessResponse
import com.alejandro.plana.profile.google.domain.SignOutResponse
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Esta clase es la encargada de manejar el perfil del usuario con Google.
 */
@Singleton
class ProfileRepositoryGoogle @Inject constructor(
    private val auth: FirebaseAuth,
    private var oneTapClient: SignInClient,
    private var signInClient: GoogleSignInClient,
    private val db: FirebaseFirestore
) : ProfileGoogleRepository {
    override val displayName = auth.currentUser?.displayName.toString()
    override val photoUrl = auth.currentUser?.photoUrl.toString()

    /**
     * Este método es el encargado de cerrar sesión con Google.
     * Primero se cierra sesión con GoogleOneTap.
     * Después se cierra sesión con Firebase.
     * @return success o failure
     */
    override suspend fun signOut(): SignOutResponse {
        return try {
            oneTapClient.signOut().await()
            auth.signOut()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    /**
     * Este método es el encargado de revocar el acceso con Google.
     * Verifica si hay un usuario actualmente autenticado.
     * Si hay un usuario autenticado, se elimina el documento de la base de datos de Firebase.
     * Se revoca el acceso con GoogleOneTap.
     * Se elimina la cuenta de Firebase.
     * @return success o failure
     */
    override suspend fun revokeAccess(): RevokeAccessResponse {
        return try {
            auth.currentUser?.apply {
                db.collection(USERS).document(uid).delete().await()
                signInClient.revokeAccess().await()
                oneTapClient.signOut().await()
                delete().await()
            }
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }
}