package com.example.mindvocab.model.utils

import java.lang.Exception

typealias Mapper<Input, Output> = (Input) -> Output

sealed class Result<T> {
    fun <R> map(mapper: Mapper<T, R>? = null) : Result<R> {
        return when(this) {
            is PendingResult -> PendingResult()
            is ErrorResult -> ErrorResult(this.exception)
            is SuccessResult -> {
                if (mapper == null) throw IllegalArgumentException("Mapper should not be null for success result")
                SuccessResult(mapper(this.data))
            }
        }
    }
}

class PendingResult<T> : Result<T>()
class SuccessResult<T>(val data: T) : Result<T>()
class ErrorResult<T>(val exception: Exception) : Result<T>()


fun <T> Result<T>?.successResult() : T? {
    return if (this is SuccessResult) {
        return this.data
    } else {
        return null
    }
}
