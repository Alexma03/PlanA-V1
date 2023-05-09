package com.alejandro.plana.inicio.email.domain.model

sealed class ResponseEmail<out T> {
    object Loading: ResponseEmail<Nothing>()

    data class Success<out T>(
        val data: T
    ): ResponseEmail<T>()

    data class Failure(
        val e: Exception
    ): ResponseEmail<Nothing>()
}
