package com.alejandro.plana.inicio.email.di

import com.alejandro.plana.inicio.email.data.repository.AuthRepositoryEmailImpl
import com.alejandro.plana.inicio.email.domain.repository.AuthRepositoryEmail
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent

class AppModule {
    @Module
    @InstallIn(ViewModelComponent::class)
    class AppModule {
        @Provides
        fun provideAuthRepository(): AuthRepositoryEmail = AuthRepositoryEmailImpl(
            auth = Firebase.auth
        )
    }

    @Module
    @InstallIn(ActivityComponent::class)
    object AuthModule {
        @Provides
        fun provideAuthRepositoryEmail(auth: FirebaseAuth): AuthRepositoryEmail {
            return AuthRepositoryEmailImpl(auth)
        }
    }


}