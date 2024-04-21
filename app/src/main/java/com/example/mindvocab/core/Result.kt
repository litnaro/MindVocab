package com.example.mindvocab.core

import com.example.mindvocab.model.AppException

typealias Mapper<Input, Output> = (Input) -> Output

sealed class Result<out T> {

    data object Pending : Result<Nothing>()

    class Success<T>(val data: T) : Result<T>()

    class Error<T>(val exception: AppException) : Result<T>()

    fun <R> map(mapper: Mapper<T, R>? = null) : Result<R> {
        return when(this) {
            is Pending -> Pending
            is Error -> Error(exception)
            is Success -> {
                if (mapper == null) throw IllegalArgumentException("Mapper should not be null for success result")
                Success(mapper(this.data))
            }
        }
    }
}

@Suppress("unused", "UNREACHABLE_CODE")
fun <T> Result<T>?.successResult() : T? {
    return if (this is Result.Success) {
        return this.data
    } else {
        return null
    }
}
