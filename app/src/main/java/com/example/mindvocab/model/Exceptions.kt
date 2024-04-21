package com.example.mindvocab.model

abstract class AppException : RuntimeException()

/**
 * If unable to execute SQL query.
 */
class StorageException : AppException()

/**
 * User is not authorized.
 */
class AuthException : AppException()


