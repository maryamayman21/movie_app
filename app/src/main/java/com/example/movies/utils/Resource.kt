package com.example.movies.utils

import com.example.movies.data.AppError

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val error: AppError) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}