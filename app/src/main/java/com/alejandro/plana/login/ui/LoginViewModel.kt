package com.alejandro.plana.login.ui

import android.util.Patterns
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class LoginViewModel {

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