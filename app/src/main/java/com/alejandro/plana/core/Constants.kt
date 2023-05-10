package com.alejandro.plana.core

object Constants {
    const val TAG = "AppTag"

    //Collection References
    const val USERS = "users"

    //User fields
    const val DISPLAY_NAME = "displayName"
    const val EMAIL = "email"
    const val PHOTO_URL = "photoUrl"
    const val CREATED_AT = "createdAt"

    //Names
    const val SIGN_IN_REQUEST = "signInRequest"
    const val SIGN_UP_REQUEST = "signUpRequest"

    //Buttons
    const val SIGN_IN_WITH_GOOGLE = "Sign in with Google"
    const val SIGN_OUT = "Sign-out"
    const val REVOKE_ACCESS = "Revoke Access"

    //Screens
    const val INICIO_SCREEN = "Authentication"
    const val PROFILE_SCREEN = "Profile"
    const val REGISTRO_SCREEN = "Registro"
    const val HOME_SCREEN = "Home"
    const val LOGIN_SCREEN = "Login"
    const val FORGOT_PASSWORD_SCREEN = "ForgotPassword"
    const val VERIFY_EMAIL_SCREEN = "VerifyEmail"

    //Messages
    const val REVOKE_ACCESS_MESSAGE = "You need to re-authenticate before revoking the access."

    //Error Messages
    const val SENSITIVE_OPERATION_MESSAGE = "This operation is sensitive and requires recent authentication. Log in again before retrying this request."

}