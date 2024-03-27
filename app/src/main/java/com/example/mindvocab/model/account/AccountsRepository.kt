package com.example.mindvocab.model.account

import com.example.mindvocab.model.Repository
import com.example.mindvocab.model.account.etities.Account
import com.example.mindvocab.model.account.etities.SignUpData
import kotlinx.coroutines.flow.Flow

interface AccountsRepository : Repository {

    suspend fun getAccount() : Flow<Account?>

    suspend fun isSignedIn(): Boolean

    suspend fun signIn(login: String, password: CharArray) : Long

    suspend fun signUp(signUpData: SignUpData)

    suspend fun logout()

    suspend fun updateUsername(newUsername: String)

    suspend fun changePassword(newPassword: String)

    suspend fun setAccountPhoto(photo: String)

}