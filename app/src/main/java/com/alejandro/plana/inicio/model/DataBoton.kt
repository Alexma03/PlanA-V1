package com.alejandro.plana.inicio.model

import androidx.compose.ui.graphics.Color

data class DataBoton(
    val idPhoto: Int,
    val nombre: String,
    val texto: String,
    val textColor: Color,
    val backgroundColor: Color,
    val onClicked: (() -> Unit)?
)