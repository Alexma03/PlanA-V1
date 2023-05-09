package com.alejandro.plana.inicio.di

import android.app.Application
import android.content.Context
import com.alejandro.plana.R
import com.alejandro.plana.core.Constants.SIGN_IN_REQUEST
import com.alejandro.plana.core.Constants.SIGN_UP_REQUEST
import com.alejandro.plana.profile.google.data.ProfileRepositoryGoogle
import com.alejandro.plana.inicio.google.data.repository.AuthRepositoryGoogle
import com.alejandro.plana.inicio.google.domain.repository.AuthGoogleRepository
import com.alejandro.plana.profile.google.domain.ProfileGoogleRepository
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Named

/**
 * Este módulo es el encargado de proveer las dependencias del inicio de sesión con Google.
 */
@Module
@InstallIn(ViewModelComponent::class)
class AppModule {
    /**
     * Provee el cliente de autenticación de Google.
     */
    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    /**
     * Provee el cliente de base de datos de Firestore.
     */
    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore


    /**
     * Proporciona la instancia del SignInClient necesario para iniciar sesión con GoogleOneTap.
     * @param context Se utiliza para obtener la instancia del SignInClient.
     * Usa el metodo getSignInClient de la clase Identity para obtener y devolver el SignInClient.
     */
    @Provides
    fun provideOneTapClient(
        // Se usa esta anotacion para asegurarse de que el contexto sea el de la aplicación permitiendo
        // que esten disponibles en el ciclo de vida de la aplicación.
        @ApplicationContext
        context: Context
    ) = Identity.getSignInClient(context)

    /**
     * Es una instancia de BeginSignInRequest que se usa para iniciar sesión con GoogleOneTap.
     * Esta configurada para que solo puedan iniciar sesion las cuentas que el usuario haya autorizado
     * usando el metodo setFilterByAuthorizedAccounts(true). Ademas usa el metodo setAutoSelectEnabled(true)
     * para que se inicie sesión automaticamente con la cuenta de Google autorizada.
     * Marcado con la etiqueta @Named para diferenciarse con la configuracion de SIGN_UP_REQUEST
     * @param app Se usa para obtener el id del cliente web.
     */
    @Provides
    @Named(SIGN_IN_REQUEST)
    fun provideSignInRequest(
        app: Application
    ) = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(app.getString(R.string.web_client_id))
                .setFilterByAuthorizedAccounts(true)
                .build())
        .setAutoSelectEnabled(true)
        .build()

    /**
     * Es una instancia de BeginSignInRequest que se usa para iniciar sesión con GoogleOneTap.
     * Esta configurada para que puedan iniciar sesion todas las cuentas de Google del dispositivo
     * usando el metodo setFilterByAuthorizedAccounts(false).
     * Marcado con la etiqueta @Named para diferenciarse con la configuracion de SIGN_IN_REQUEST
     * @param app Se usa para obtener el id del cliente web.
     */
    @Provides
    @Named(SIGN_UP_REQUEST)
    fun provideSignUpRequest(
        app: Application
    ) = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(app.getString(R.string.web_client_id))
                .setFilterByAuthorizedAccounts(false)
                .build())
        .build()

    /**
     * Provee la configuración de inicio de sesión con Google pidiendo los datos de correo electrónico del usuario.
     * @param app Se usa para obtener el id del cliente web.
     */
    @Provides
    fun provideGoogleSignInOptions(
        app: Application
    ) = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(app.getString(R.string.web_client_id))
        .requestEmail()
        .build()

    /**
     * Provee los datos del usuario que se ha autenticado con Google.
     */
    @Provides
    fun provideGoogleSignInClient(
        app: Application,
        options: GoogleSignInOptions
    ) = GoogleSignIn.getClient(app, options)

    /**
     * Proporciona la instancia del repositorio de autenticación de Google.
     * Crea una instancia de AuthRepositoryGoogle utilizando los parametros que se le pasan
     * y la devuelve permitiendo que se utilice y se inyecte en otras clases y componentes.
     * @param auth Firebase Auth para autenticar y gestionar la sesión del usuario.
     * @param oneTapClient SignInClient Cliente de inicio de sesión con GoogleOneTap.
     * @param signInRequest BeginSignInRequest Configuración de inicio de sesión con GoogleOneTap.
     * @param signUpRequest BeginSignInRequest Configuración de registro de sesión con GoogleOneTap.
     * @param db FirebaseFirestore para acceder a la base de datos de Firestore.
     */
    @Provides
    fun provideAuthRepository(
        auth: FirebaseAuth,
        oneTapClient: SignInClient,
        @Named(SIGN_IN_REQUEST)
        signInRequest: BeginSignInRequest,
        @Named(SIGN_UP_REQUEST)
        signUpRequest: BeginSignInRequest,
        db: FirebaseFirestore
    ): AuthGoogleRepository = AuthRepositoryGoogle(
        auth = auth,
        oneTapClient = oneTapClient,
        signInRequest = signInRequest,
        signUpRequest = signUpRequest,
        db = db
    )

    /**
     * Proporciona la instancia del repositorio de perfil de Google.
     * Crea una instancia de ProfileRepositoryGoogle utilizando los parametros que se le pasan
     * y la devuelve permitiendo que se utilice y se inyecte en otras clases y componentes.
     * @param auth Firebase Auth para autenticar y gestionar la sesión del usuario.
     * @param oneTapClient SignInClient Cliente de inicio de sesión con GoogleOneTap.
     * @param signInClient GoogleSignInClient Cliente de inicio de sesión con Google.
     * @param db FirebaseFirestore para acceder a la base de datos de Firestore.
     */
    @Provides
    fun provideProfileRepository(
        auth: FirebaseAuth,
        oneTapClient: SignInClient,
        signInClient: GoogleSignInClient,
        db: FirebaseFirestore
    ): ProfileGoogleRepository = ProfileRepositoryGoogle(
        auth = auth,
        oneTapClient = oneTapClient,
        signInClient = signInClient,
        db = db
    )
}