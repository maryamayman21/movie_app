package com.example.movies.data

import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

sealed class AppError(
    override val message: String? = null,
    override val cause: Throwable? = null
) : Exception(message, cause) {
    data class NetworkError(override val cause: Throwable?) : AppError(cause = cause)
    data class ApiError(
        val code: Int,
        override val message: String?
    ) : AppError(message = message)

    object NoInternetError : AppError(message = "No internet connection")
    object NotFoundError : AppError(message = "Resource not found")
}

class ApiErrorHandler
@Inject
constructor()
{
    fun handle(throwable: Throwable): AppError = when (throwable) {
        is IOException -> if (throwable is SocketTimeoutException) {
            AppError.NetworkError(throwable)
        } else {
            AppError.NoInternetError
        }
        is HttpException -> when (throwable.code()) {
            404 -> AppError.NotFoundError
            else -> AppError.ApiError(throwable.code(), throwable.message())
        }
        else -> AppError.NetworkError(throwable)
    }
}