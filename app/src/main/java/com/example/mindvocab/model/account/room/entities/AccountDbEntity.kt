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
    val name: String?,
    val surname: String?,
    val username: String,
    @ColumnInfo(name = "email", collate = ColumnInfo.NOCASE) val email: String,
    val photo: String?,
    val password: String,
    @ColumnInfo(name = "created_at") val createdAt: Long
) {
    fun toAccount() : Account = Account(
        id = id,
        name = name ?: "",
        surname = surname ?: "",
        username = username,
        email = email,
        photo = photo ?: "",
        password = password,
        createdAt = createdAt
    )
}