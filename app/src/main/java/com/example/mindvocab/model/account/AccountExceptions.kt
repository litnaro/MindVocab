package com.example.mindvocab.model.account

import com.example.mindvocab.model.AppException

enum class Field {
    Email,
    Username,
    Password,
}

class EmptyFieldException(val field: Field) : AppException()

class PasswordMismatchException : AppException()

class AccountAlreadyExistsException : AppException()

class SameDataModificationException : AppException()