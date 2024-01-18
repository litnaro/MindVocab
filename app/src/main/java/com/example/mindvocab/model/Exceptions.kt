package com.example.mindvocab.model

open class AppException : RuntimeException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(cause: Throwable) : super(cause)
}

class UnableToLoadWordException(message: String) : AppException(message = message)

class UnableToListenInformationException(message: String) : AppException(message = message)

class WordsEndedException(message: String) : AppException(message = message)