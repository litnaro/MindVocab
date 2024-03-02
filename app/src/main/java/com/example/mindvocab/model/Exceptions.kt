package com.example.mindvocab.model

open class AppException : RuntimeException()

//DB exceptions
class StorageException : AppException()

// Word set exception
class WordSetAlreadyExistsException : AppException()

//Learn exceptions
class NoWordsToLearnException : AppException()

//Repeat exceptions
class NoWordsToRepeatException : AppException()

class WordsToRepeatCurrentlyInTimeout : AppException()

//Account exception
class AuthException : AppException()

