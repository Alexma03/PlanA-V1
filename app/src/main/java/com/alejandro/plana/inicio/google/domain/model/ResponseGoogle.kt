package com.alejandro.plana.inicio.google.domain.model

/**
 * Clase sellada que representa el estado de la respuesta de una petición a Firebase
 * @param T puede ser cualquier tipo de dato
 * @property Loading representa que la petición está en curso
 * @property Success representa que la petición fue exitosa y contiene los datos de la respuesta
 * @property Failure representa que la petición falló y contiene la excepción
 */
sealed class ResponseGoogle<out T> {
    object Loading: ResponseGoogle<Nothing>()

    data class Success<out T>(
        val data: T?
    ): ResponseGoogle<T>()

    data class Failure(
        val e: Exception
    ): ResponseGoogle<Nothing>()
}
