package com.example.mindvocab.model.account

import com.example.mindvocab.model.Repository
import com.example.mindvocab.model.account.entities.Account
import com.example.mindvocab.model.account.entities.SignUpData
import com.example.mindvocab.model.AuthException
import com.example.mindvocab.model.StorageException
import com.example.mindvocab.model.account.entities.ChangePasswordData
import com.example.mindvocab.model.account.entities.FullName
import kotlinx.coroutines.flow.Flow

interface AccountsRepository : Repository {

    /**
     * Get the account info of the current signed-in user.
     */
    fun getAccount() : Flow<Account?>

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
     * Get current account username.
     * @throws [AuthException]
     */
    suspend fun getAccountUsername() : String

    /**
     * Change the username of the current signed-in user.
     * @throws [EmptyFieldException]
     * @throws [AuthException]
     * @throws [StorageException]
     * @throws [SameDataModificationException]
     */
    suspend fun updateUsername(newUsername: String)

    /**
     * Get current account full name.
     * @throws [AuthException]
     */
    suspend fun getAccountFullName() : FullName

    /**
     * Change the full name of the current signed-in user.
     * @throws [AuthException]
     */
    suspend fun updateFullName(fullName: FullName)

    /**
     * Change the password name of the current signed-in user.
     * @throws [AuthException]
     * @throws [EmptyFieldException]
     * @throws [PasswordMismatchException]
     * @throws [SameDataModificationException]
     */
    suspend fun changePassword(changePasswordData: ChangePasswordData)

    /**
     * Delete all word sets, words and achievements progress.
     */
    suspend fun resetAccountProgress()

    //TODO implementation
    suspend fun setAccountPhoto(photo: String)

}