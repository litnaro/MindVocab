package com.example.mindvocab.model

open class AppException : RuntimeException()

//DB exceptions
class StorageException : AppException()

// Word set exception


//Learn exceptions
class NoWordsToLearnException : AppException()

//Account exception
class AuthException : AppException()

