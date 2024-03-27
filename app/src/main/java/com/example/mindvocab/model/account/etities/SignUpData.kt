package com.example.mindvocab.model.account.etities

import com.example.mindvocab.model.EmptyFieldException
import com.example.mindvocab.model.Field
import com.example.mindvocab.model.PasswordMismatchException

data class SignUpData(
    val username: String,
    val email: String,
    val password: CharArray,
    val repeatPassword: CharArray
) {
    fun validate() {
        if (email.isBlank()) throw EmptyFieldException(Field.Email)
        if (username.isBlank()) throw EmptyFieldException(Field.Username)
        if (password.isEmpty()) throw EmptyFieldException(Field.Password)
        if (!password.contentEquals(repeatPassword)) throw PasswordMismatchException()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SignUpData

        if (username != other.username) return false
        if (email != other.email) return false
        if (!password.contentEquals(other.password)) return false
        if (!repeatPassword.contentEquals(other.repeatPassword)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + password.contentHashCode()
        result = 31 * result + repeatPassword.contentHashCode()
        return result
    }
}
