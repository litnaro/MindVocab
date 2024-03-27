package com.example.mindvocab.model.account.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.mindvocab.model.account.etities.Account
import com.example.mindvocab.model.account.etities.SignUpData
import com.example.mindvocab.model.account.security.SecurityUtils

@Entity(
    tableName = "accounts",
    indices = [
        Index("email", unique = true)
    ]
)
data class AccountDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String?,
    val surname: String?,
    val username: String,
    @ColumnInfo(name = "email", collate = ColumnInfo.NOCASE) val email: String,
    val photo: String?,
    @ColumnInfo(name = "hash") val hash: String,
    @ColumnInfo(name = "salt", defaultValue = "") val salt: String,
    @ColumnInfo(name = "created_at") val createdAt: Long
) {

    fun toAccount() : Account = Account(
        id = id,
        name = name ?: "",
        surname = surname ?: "",
        username = username,
        email = email,
        photo = photo ?: "",
        createdAt = createdAt
    )

    companion object {
        fun fromSignUpData(signUpData: SignUpData, securityUtils: SecurityUtils): AccountDbEntity {
            val salt = securityUtils.generateSalt()
            val hash = securityUtils.passwordToHash(signUpData.password, salt)
            signUpData.password.fill('*')
            signUpData.repeatPassword.fill('*')
            return AccountDbEntity(
                id = 0,
                email = signUpData.email,
                username = signUpData.username,
                name = null,
                surname = null,
                photo = null,
                hash = securityUtils.bytesToString(hash),
                salt = securityUtils.bytesToString(salt),
                createdAt = System.currentTimeMillis()
            )
        }
    }
}