package com.example.mindvocab.model.account.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.mindvocab.model.account.etities.Account

@Entity(
    tableName = "accounts",
    indices = [
        Index("email", unique = true)
    ]
)
data class AccountDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val surname: String,
    val username: String,
    @ColumnInfo(name = "email", collate = ColumnInfo.NOCASE) val email: String,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB) val photo: ByteArray,
    val password: String,
    @ColumnInfo(name = "created_at") val createdAt: Long
) {
    fun toAccount() : Account = Account(
        id = id,
        name = name,
        surname = surname,
        username = username,
        email = email,
        photo = "photo",
        password = password,
        createdAt = createdAt
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AccountDbEntity

        if (id != other.id) return false
        if (name != other.name) return false
        if (surname != other.surname) return false
        if (username != other.username) return false
        if (email != other.email) return false
        if (password != other.password) return false
        if (createdAt != other.createdAt) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + surname.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + createdAt.hashCode()
        return result
    }
}