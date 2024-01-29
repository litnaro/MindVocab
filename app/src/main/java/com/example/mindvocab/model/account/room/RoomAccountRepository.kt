package com.example.mindvocab.model.account.room

import com.example.mindvocab.model.account.AccountRepository
import com.example.mindvocab.model.account.etities.Account
import com.example.mindvocab.model.account.etities.SignUpData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomAccountRepository(
    private val accountsDao: AccountsDao
) : AccountRepository {

    override suspend fun isSignedIn(): Boolean {
        return true
    }

    override suspend fun signIn(email: String, password: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun signUp(signUpData: SignUpData) {
        TODO("Not yet implemented")
    }

    override suspend fun logout() {
        TODO("Not yet implemented")
    }

    override suspend fun getAccount(): Flow<Account?> {
        return accountsDao.getAccountById(1).map {
            it.toAccount()
        }
    }

    override suspend fun updateUsername(username: String) {
        TODO("Not yet implemented")
    }

    override suspend fun changePassword(password: String, repeatPassword: String) {
        TODO("Not yet implemented")
    }

    override suspend fun setAccountPhoto(photo: String) {
        TODO("Not yet implemented")
    }


}