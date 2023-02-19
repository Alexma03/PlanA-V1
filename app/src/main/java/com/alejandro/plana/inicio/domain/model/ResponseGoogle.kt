package com.alejandro.plana.inicio.domain.model

sealed class ResponseGoogle<out T> {
    object Loading: ResponseGoogle<Nothing>()

    data class Success<out T>(
        val data: T?
    ): ResponseGoogle<T>()

    data class Failure(
        val e: Exception
    ): ResponseGoogle<Nothing>()
}
