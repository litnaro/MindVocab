package com.example.mindvocab.model.account

import com.example.mindvocab.model.Repository
import com.example.mindvocab.model.account.etities.Account
import com.example.mindvocab.model.account.etities.SignUpData
import kotlinx.coroutines.flow.Flow

interface AccountRepository : Repository {

    suspend fun isSignedIn(): Boolean

    suspend fun signIn(email: String, password: String) : String

    suspend fun signUp(signUpData: SignUpData)

    suspend fun logout()

    suspend fun getAccount() : Flow<Account?>

    suspend fun updateUsername(username: String)

    suspend fun changePassword(password: String, repeatPassword: String)

    suspend fun setAccountPhoto(photo: String)

}