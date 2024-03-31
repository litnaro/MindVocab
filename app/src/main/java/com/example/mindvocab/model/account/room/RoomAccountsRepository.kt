package com.example.mindvocab.model.account.room

import android.database.sqlite.SQLiteConstraintException
import com.example.mindvocab.model.AccountAlreadyExistsException
import com.example.mindvocab.model.AuthException
import com.example.mindvocab.model.EmptyFieldException
import com.example.mindvocab.model.Field
import com.example.mindvocab.model.SameDataModificationException
import com.example.mindvocab.model.account.AccountsRepository
import com.example.mindvocab.model.account.etities.Account
import com.example.mindvocab.model.account.etities.ChangePasswordData
import com.example.mindvocab.model.account.etities.FullName
import com.example.mindvocab.model.account.etities.SignUpData
import com.example.mindvocab.model.account.room.entities.AccountDbEntity
import com.example.mindvocab.model.account.room.entities.AccountUpdateFullNameTuple
import com.example.mindvocab.model.account.room.entities.AccountUpdatePasswordTuple
import com.example.mindvocab.model.account.room.entities.AccountUpdateUsernameTuple
import com.example.mindvocab.model.account.security.SecurityUtils
import com.example.mindvocab.model.room.wrapSQLiteException
import com.example.mindvocab.model.settings.account.AccountSettings
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomAccountsRepository @Inject constructor(
    private val accountsDao: AccountsDao,
    private val securityUtils: SecurityUtils,
    private val accountSettings: AccountSettings,
    private val ioDispatcher: CoroutineDispatcher
) : AccountsRepository {

    private val currentAccountIdFlow = MutableStateFlow(AccountId(accountSettings.getCurrentAccountId()))

    override suspend fun isSignedIn(): Boolean {
        return accountSettings.getCurrentAccountId() != AccountSettings.NO_ACCOUNT_ID
    }

    override suspend fun signIn(login: String, password: CharArray) : Long {
        if (login.isBlank()) throw EmptyFieldException(Field.Email)
        if (password.isEmpty()) throw EmptyFieldException(Field.Password)

        val accountId: Long = if (login.contains("@")) {
            findAccountIdByEmailAndPassword(login, password)
        } else {
            findAccountIdByUsernameAndPassword(login, password)
        }

        accountSettings.setCurrentAccountId(accountId)
        currentAccountIdFlow.emit(AccountId(accountId))

        return accountId
    }

    override suspend fun signUp(signUpData: SignUpData) {
        signUpData.validate()
        createAccount(signUpData)
    }

    override suspend fun logout() {
        accountSettings.setCurrentAccountId(AccountSettings.NO_ACCOUNT_ID)
        currentAccountIdFlow.emit(AccountId(AccountSettings.NO_ACCOUNT_ID))
    }

    override suspend fun getAccountUsername(): String {
        val account = getAccount().first() ?: throw AuthException()
        return account.username
    }

    override suspend fun updateUsername(newUsername: String) = wrapSQLiteException(ioDispatcher) {
        if (newUsername.isBlank()) throw EmptyFieldException(Field.Username)
        if (newUsername == getAccountUsername()) throw SameDataModificationException()

        val accountId = accountSettings.getCurrentAccountId()
        if (accountId == AccountSettings.NO_ACCOUNT_ID) throw AuthException()

        accountsDao.updateUsername(AccountUpdateUsernameTuple(accountId, newUsername))
    }

    override suspend fun getAccountFullName(): FullName {
        val account = getAccount().first() ?: throw AuthException()
        return FullName(
            account.name,
            account.surname
        )
    }

    override suspend fun updateFullName(fullName: FullName) = wrapSQLiteException(ioDispatcher) {
        val (name, surname) = fullName

        val account = getAccount().first() ?: throw AuthException()

        accountsDao.updateFullName(
            AccountUpdateFullNameTuple(account.id, name, surname)
        )
    }

    override suspend fun changePassword(changePasswordData: ChangePasswordData) = wrapSQLiteException(ioDispatcher) {
        changePasswordData.validate()

        val account = getAccount().first() ?: throw AuthException()

        findAccountIdByEmailAndPassword(account.email, changePasswordData.oldPassword)

        val newSalt = securityUtils.generateSalt()
        val newHash = securityUtils.passwordToHash(changePasswordData.newPassword, newSalt)

        changePasswordData.clearFields()

        accountsDao.updatePassword(
            AccountUpdatePasswordTuple(
                id = account.id,
                hash = securityUtils.bytesToString(newHash),
                salt = securityUtils.bytesToString(newSalt)
            )
        )
    }

    override suspend fun setAccountPhoto(photo: String) = wrapSQLiteException(ioDispatcher) {
        TODO("Not yet implemented")
    }

    override suspend fun getAccount(): Flow<Account?> {
        return getAccountById(accountSettings.getCurrentAccountId())
    }

    private suspend fun findAccountIdByEmailAndPassword(email: String, password: CharArray): Long {
        val tuple = accountsDao.findByEmail(email) ?: throw AuthException()

        val saltBytes = securityUtils.stringToBytes(tuple.salt)
        val hashBytes = securityUtils.passwordToHash(password, saltBytes)
        val hashString = securityUtils.bytesToString(hashBytes)
        password.fill('*')
        if (tuple.hash != hashString) throw AuthException()
        return tuple.id
    }

    private suspend fun findAccountIdByUsernameAndPassword(username: String, password: CharArray): Long {
        val tuple = accountsDao.findByUsername(username) ?: throw AuthException()

        val saltBytes = securityUtils.stringToBytes(tuple.salt)
        val hashBytes = securityUtils.passwordToHash(password, saltBytes)
        val hashString = securityUtils.bytesToString(hashBytes)
        password.fill('*')
        if (tuple.hash != hashString) throw AuthException()
        return tuple.id
    }

    private suspend fun createAccount(signUpData: SignUpData) = withContext(ioDispatcher) {
        try {
            val entity = AccountDbEntity.fromSignUpData(signUpData, securityUtils)
            accountsDao.createAccount(entity)
        } catch (e: SQLiteConstraintException) {
            throw AccountAlreadyExistsException()
        }
    }

    private fun getAccountById(accountId: Long): Flow<Account?> {
        return accountsDao.getAccountById(accountId).map { it?.toAccount() }
    }

    @Suppress("unused")
    private class AccountId(val value: Long)

}