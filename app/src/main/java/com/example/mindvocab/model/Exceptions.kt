package com.example.mindvocab.model

open class AppException : RuntimeException()

class StorageException : AppException()

// Word set exception
class WordSetAlreadyExistsException : AppException()

class UnableToLoadWordException(message: String) : AppException()

class UnableToListenInformationException(message: String) : AppException()

class WordsEndedException(message: String) : AppException()

//Account exception
class AuthException : AppException()

