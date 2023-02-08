package com.alejandro.plana.resgistro.ui

import android.util.Patterns
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistroViewModel @Inject constructor(): ViewModel() {

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

    fun onLoginChanged(
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

    fun enableSingUp(
        email: String,
        password: String,
        name: String,
        repeatPassword: String,
    ): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length > 5 &&
                name.isNotEmpty() && repeatPassword == password


    //textos de error

    fun colorTextorequisitos(texto: String, condicion: Int = 1, repeatPassword: String = ""): Color {
        if (condicion == 1) {
            if (texto.isBlank()) {
                return Color.Red
            } else {
                return Color.Gray
            }
        } else if (condicion == 2) {
            if (texto.length < 6) {
                return Color.Red
            } else {
                return Color.Gray
            }
        } else if (condicion == 3) {
            if (texto.isBlank() or repeatPassword.isBlank()){
                return Color.Red
            } else if (texto == repeatPassword) {
                return Color.Gray
            } else {
                return Color.Red
            }
        } else if (condicion == 4) {
            if (texto.length < 5) {
                return Color.Red
            } else {
                return Color.Gray
            }
        } else {
            if (Patterns.EMAIL_ADDRESS.matcher(texto).matches()) {
                return Color.Gray
            } else {
                return Color.Red
            }
        }
    }

}