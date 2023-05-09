package com.alejandro.plana.inicio.google.data.repository

import com.alejandro.plana.core.Constants.CREATED_AT
import com.alejandro.plana.core.Constants.DISPLAY_NAME
import com.alejandro.plana.core.Constants.EMAIL
import com.alejandro.plana.core.Constants.PHOTO_URL
import com.alejandro.plana.core.Constants.SIGN_IN_REQUEST
import com.alejandro.plana.core.Constants.SIGN_UP_REQUEST
import com.alejandro.plana.core.Constants.USERS
import com.alejandro.plana.inicio.domain.model.Response.*
import com.alejandro.plana.inicio.domain.repository.OneTapSignInResponse
import com.alejandro.plana.inicio.google.domain.repository.AuthGoogleRepository
import com.alejandro.plana.inicio.domain.repository.SignInWithGoogleResponse
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue.serverTimestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * Esta clase es la encargada de manejar la autenticación con Google.
 */
@Singleton
open class AuthRepository  @Inject constructor(
    private val auth: FirebaseAuth,
    private var oneTapClient: SignInClient,
    @Named(SIGN_IN_REQUEST)
    private var signInRequest: BeginSignInRequest,
    @Named(SIGN_UP_REQUEST)
    private var signUpRequest: BeginSignInRequest,
    private val db: FirebaseFirestore
) : AuthGoogleRepository {
    override val isUserAuthenticatedInFirebase = auth.currentUser != null

    /**
     * Este método es el encargado de autenticacion con GoogleOneTap.
     * Primero se intenta iniciar sesión con la cuenta de Google que se tiene en el dispositivo.
     * Si no se tiene ninguna cuenta de Google en el dispositivo, se muestra el selector de cuentas
     * @return success o failure
     */
    override suspend fun oneTapSignInWithGoogle(): OneTapSignInResponse {
        return try {
            // Intenta iniciar sesión con la cuenta autorizada por el usuario en la seleccion de cuentas
            val signInResult = oneTapClient.beginSignIn(signInRequest).await()
            Success(signInResult)
        } catch (e: Exception) {
            try {
                // En caso de que no funcione se mostraran todas las cuentas de Google que se tienen
                val signUpResult = oneTapClient.beginSignIn(signUpRequest).await()
                Success(signUpResult)
            } catch (e: Exception) {
                Failure(e)
            }
        }
    }

    /**
     * Este metodo se encarga del proceso de autenticación en Firebase utilizando las credenciales
     * de una cuenta de Google. Verifica si el usuario ya existe en la base de datos de Firebase.
     * Si no existe llama al metodo addUserToFirestore() para agregarlo.
     * @param googleCredential es el token que se obtiene al iniciar sesión con Google
     * @return success o failure
     */
    override suspend fun firebaseSignInWithGoogle(
        googleCredential: AuthCredential
    ): SignInWithGoogleResponse {
        return try {
            // Usa el token
            val authResult = auth.signInWithCredential(googleCredential).await()
            // Verifica si existe
            val isNewUser = authResult.additionalUserInfo?.isNewUser ?: false
            // Si no existe lo agrega a la base de datos
            if (isNewUser) {
                addUserToFirestore()
            }
            // Retorna success
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    /**
     * Este metodo se encarga de añaadir el usuario a Firestore
     * Obtiene el usuario actual y lo agrega a la base de datos
     */
    private suspend fun addUserToFirestore() {
        // lo obtiene de Firebase Auth
        auth.currentUser?.apply {
            // convierte la informacion del usuario a un mapa de datos
            val user = toUser()
            // utiliza el uid como id del documento
            db.collection(USERS).document(uid).set(user).await()
        }
    }
}

/**
 * Esta funcion extiende la clase FirebaseUser y convierte la informacion del usuario a un mapa de datos
 * @return un mapa de datos con la informacion del usuario
 */
fun FirebaseUser.toUser() = mapOf(
    DISPLAY_NAME to displayName,
    EMAIL to email,
    PHOTO_URL to photoUrl?.toString(),
    CREATED_AT to serverTimestamp()
)