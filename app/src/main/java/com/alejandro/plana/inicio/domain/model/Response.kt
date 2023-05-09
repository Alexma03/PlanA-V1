package com.alejandro.plana.inicio.domain.model

/**
 * Clase sellada que representa el estado de la respuesta de una petición a Firebase
 * @param T puede ser cualquier tipo de dato
 * @property Loading representa que la petición está en curso
 * @property Success representa que la petición fue exitosa y contiene los datos de la respuesta
 * @property Failure representa que la petición falló y contiene la excepción
 */
sealed class Response<out T> {
    object Loading: Response<Nothing>()

    data class Success<out T>(
        val data: T?
    ): Response<T>()

    data class Failure(
        val e: Exception
    ): Response<Nothing>()
}
