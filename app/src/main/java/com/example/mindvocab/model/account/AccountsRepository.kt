package com.example.mindvocab.model.account

import com.example.mindvocab.model.Repository
import com.example.mindvocab.model.account.etities.Account
import com.example.mindvocab.model.account.etities.SignUpData
import com.example.mindvocab.model.EmptyFieldException
import com.example.mindvocab.model.AuthException
import com.example.mindvocab.model.StorageException
import com.example.mindvocab.model.PasswordMismatchException
import com.example.mindvocab.model.AccountAlreadyExistsException
import kotlinx.coroutines.flow.Flow

interface AccountsRepository : Repository {

    /**
     * Get the account info of the current signed-in user.
     */
    suspend fun getAccount() : Flow<Account?>

    /**
     * Whether user is signed-in or not.
     */
    suspend fun isSignedIn(): Boolean

    /**
     * Try to sign-in with the email and password.
     * @throws [EmptyFieldException]
     * @throws [AuthException]
     * @throws [StorageException]
     */
    suspend fun signIn(login: String, password: CharArray) : Long

    /**
     * Create a new account.
     * @throws [EmptyFieldException]
     * @throws [PasswordMismatchException]
     * @throws [AccountAlreadyExistsException]
     * @throws [StorageException]
     */
    suspend fun signUp(signUpData: SignUpData)

    /**
     * Sign-out from the app.
     */
    suspend fun logout()

    /**
     * Change the username of the current signed-in user.
     * @throws [EmptyFieldException]
     * @throws [AuthException]
     * @throws [StorageException]
     */
    suspend fun updateUsername(newUsername: String)

    suspend fun changePassword(newPassword: String)

    suspend fun setAccountPhoto(photo: String)

}