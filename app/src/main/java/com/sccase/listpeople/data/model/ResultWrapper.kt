package com.sccase.listpeople.data.model

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Fail(val message: String) : ResultWrapper<Nothing>()
}