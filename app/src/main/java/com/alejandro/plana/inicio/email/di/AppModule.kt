package com.alejandro.plana.inicio.email.di

import com.alejandro.plana.inicio.email.data.repository.AuthRepositoryEmailImpl
import com.alejandro.plana.inicio.email.domain.repository.AuthRepositoryEmail
import com.alejandro.plana.profile.email.data.ProfileRepositoryEmailImpl
import com.alejandro.plana.profile.email.domain.ProfilerepositoryEmail
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class AppModule {
    @Provides
    @ViewModelScoped
    fun provideAuthRepositoryEmail(auth: FirebaseAuth): AuthRepositoryEmail {
        return AuthRepositoryEmailImpl(auth)
    }
    @Provides
    @ViewModelScoped
    fun provideProfileRepositoryEmail(auth: FirebaseAuth): ProfilerepositoryEmail {
        return ProfileRepositoryEmailImpl(auth)
    }
}