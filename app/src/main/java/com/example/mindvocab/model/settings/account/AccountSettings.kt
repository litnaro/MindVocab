package com.example.mindvocab.model.settings.account

interface AccountSettings {

    suspend fun getCurrentAccountId(): Long

    suspend fun setCurrentAccountId(accountId: Long)

    companion object {
        const val NO_ACCOUNT_ID = -1L
    }
}