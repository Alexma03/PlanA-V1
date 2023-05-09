package com.alejandro.plana.resgistro.ui

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandro.plana.inicio.email.domain.model.ResponseEmail
import com.alejandro.plana.inicio.email.domain.model.ResponseEmail.*
import com.alejandro.plana.inicio.email.domain.repository.AuthRepositoryEmail
import com.alejandro.plana.inicio.email.domain.repository.SendEmailVerificationResponse
import com.alejandro.plana.inicio.email.domain.repository.SignUpResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistroViewModel @Inject constructor(
    private val repo: AuthRepositoryEmail
) : ViewModel() {
    //Logica de registro

    var signUpResponse by mutableStateOf<SignUpResponse>(Success(false))
        private set
    var sendEmailVerificationResponse by mutableStateOf<SendEmailVerificationResponse>(Success(false))
        private set

    fun signUpWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        signUpResponse = Loading
        signUpResponse = repo.firebaseSignUpWithEmailAndPassword(email, password)
    }

    fun sendEmailVerification() = viewModelScope.launch {
        sendEmailVerificationResponse = Loading
        sendEmailVerificationResponse = repo.sendEmailVerification()
    }

    //Parte de cambios de la UI

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _isSignUpEnabled = MutableLiveData<Boolean>()
    val isSignUpEnabled: LiveData<Boolean> = _isSignUpEnabled

    private val _repeatPassword = MutableLiveData<String>()
    val repeatPassword: LiveData<String> = _repeatPassword

    fun onRegisterChanged(
        name: String,
        email: String,
        password: String,
        repeatPassword: String,
        isSignUpEnabled: Boolean
    ) {
        _email.value = email
        _password.value = password
        _name.value = name
        _isSignUpEnabled.value = isSignUpEnabled
        _repeatPassword.value = repeatPassword
        _isSignUpEnabled.value = enableSingUp(email, password, name, repeatPassword)
    }

    //pasword logica

    private val _passwordVisibility = MutableLiveData<Boolean>()
    val passwordVisibility: LiveData<Boolean> = _passwordVisibility

    fun onPasswordVisibilityChanged() {
        _passwordVisibility.value = _passwordVisibility.value != true
    }


    //Parte de la logica

    private fun enableSingUp(
        email: String,
        password: String,
        name: String,
        repeatPassword: String,
    ): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length > 5 &&
                name.isNotEmpty() && repeatPassword == password


    //textos de error
    //UpperCase no funciona

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