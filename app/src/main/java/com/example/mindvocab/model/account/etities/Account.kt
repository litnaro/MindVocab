package com.example.mindvocab.model.account.etities

data class Account(
    val id: Long,
    val name: String,
    val surname: String,
    val username: String,
    val photo: String,
    val email: String,
    val createdAt: Long
)
