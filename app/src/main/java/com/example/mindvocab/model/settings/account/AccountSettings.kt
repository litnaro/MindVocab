package com.example.mindvocab.model.settings.account

interface AccountSettings {

    fun getCurrentAccountId(): Long

    fun setCurrentAccountId(accountId: Long)

    companion object {
        const val NO_ACCOUNT_ID = -1L
    }
}