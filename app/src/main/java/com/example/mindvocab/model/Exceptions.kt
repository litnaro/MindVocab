package com.example.mindvocab.model

open class AppException : RuntimeException()

//DB exceptions
class StorageException : AppException()

//Learn exceptions
class NoWordsToLearnException : AppException()

class NoMoreWordsToLearnForTodayException : AppException()

//Repeat exceptions
class NoWordsToRepeatException : AppException()

class WordsToRepeatCurrentlyInTimeout : AppException()

//Account exception
class AuthException : AppException()

enum class Field {
    Email,
    Username,
    Password,
}

class EmptyFieldException(val field: Field) : AppException()

class PasswordMismatchException : AppException()

class AccountAlreadyExistsException : AppException()

class SameDataModificationException : AppException()

