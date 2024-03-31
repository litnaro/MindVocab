package com.example.mindvocab.model.account.etities

import com.example.mindvocab.model.EmptyFieldException
import com.example.mindvocab.model.Field
import com.example.mindvocab.model.PasswordMismatchException
import com.example.mindvocab.model.SameDataModificationException

data class ChangePasswordData(
    val oldPassword: CharArray,
    val newPassword: CharArray,
    val newPasswordRepeat: CharArray
) {

    fun validate() {
        if (oldPassword.isEmpty() || newPassword.isEmpty() || newPasswordRepeat.isEmpty()) throw EmptyFieldException(Field.Password)
        if (!newPassword.contentEquals(newPasswordRepeat)) throw PasswordMismatchException()
        if (oldPassword.contentEquals(newPassword)) throw SameDataModificationException()
    }

    fun clearFields() {
        oldPassword.fill('*')
        newPassword.fill('*')
        newPasswordRepeat.fill('*')
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ChangePasswordData

        if (!oldPassword.contentEquals(other.oldPassword)) return false
        if (!newPassword.contentEquals(other.newPassword)) return false
        if (!newPasswordRepeat.contentEquals(other.newPasswordRepeat)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = oldPassword.contentHashCode()
        result = 31 * result + newPassword.contentHashCode()
        result = 31 * result + newPasswordRepeat.contentHashCode()
        return result
    }
}
