package com.alejandro.plana.login.ui

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandro.plana.inicio.email.domain.model.ResponseEmail
import com.alejandro.plana.inicio.email.domain.model.ResponseEmail.*
import com.alejandro.plana.inicio.email.domain.repository.AuthRepositoryEmail
import com.alejandro.plana.inicio.email.domain.repository.SignInResponse
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: AuthRepositoryEmail
) : ViewModel() {
    //Logica de registro

    var signInResponse by mutableStateOf<SignInResponse>(Success(false))
        private set

    fun signInWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        signInResponse = Loading
        signInResponse = repo.firebaseSignInWithEmailAndPassword(email, password)
    }

    //Parte de cambios de la UI
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _isLoginEnabled = MutableLiveData<Boolean>()
    val isLoginEnabled: LiveData<Boolean> = _isLoginEnabled

    private val _mantenerIniciada = MutableLiveData<Boolean>(false)
    val mantenerIniciada: LiveData<Boolean> = _mantenerIniciada

    private val _passwordVisibility = MutableLiveData<Boolean>()
    val passwordVisibility: LiveData<Boolean> = _passwordVisibility

    fun onLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _isLoginEnabled.value = enableLogin(email, password)
    }

    private fun enableLogin(email: String, password: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length > 5
    }

    fun onPasswordVisibilityChanged() {
        _passwordVisibility.value = _passwordVisibility.value != true
    }

    fun colorTextorequisitos(
        texto: String,
        condicion: Int = 1,
        repeatPassword: String = ""
    ): Color {
        when (condicion) {
            1 -> {
                return if (texto.isBlank()) {
                    Color.Red
                } else {
                    Color.Gray
                }
            }
            2 -> {
                return if (texto.length < 6) {
                    Color.Red
                } else {
                    Color.Gray
                }
            }
            3 -> {
                return if (texto.isBlank() or repeatPassword.isBlank()) {
                    Color.Red
                } else if (texto == repeatPassword) {
                    Color.Gray
                } else {
                    Color.Red
                }
            }
            4 -> {
                return if (texto.length < 5) {
                    Color.Red
                } else {
                    Color.Gray
                }
            }
            5 -> {
                return if (texto.any { it.isUpperCase() }) {
                    Color.Gray
                } else {
                    Color.Red
                }
            }
            else -> {
                return if (Patterns.EMAIL_ADDRESS.matcher(texto).matches()) {
                    Color.Gray
                } else {
                    Color.Red
                }
            }
        }
    }
}