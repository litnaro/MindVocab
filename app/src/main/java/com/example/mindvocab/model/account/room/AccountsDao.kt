package com.example.mindvocab.model.account.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.mindvocab.model.account.room.entities.AccountDbEntity
import com.example.mindvocab.model.account.room.entities.AccountSignInTuple
import com.example.mindvocab.model.account.room.entities.AccountUpdateFullNameTuple
import com.example.mindvocab.model.account.room.entities.AccountUpdatePasswordTuple
import com.example.mindvocab.model.account.room.entities.AccountUpdateUsernameTuple
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountsDao {

    @Query("SELECT * FROM accounts WHERE id = :accountId")
    fun getAccountById(accountId: Long) : Flow<AccountDbEntity?>

    @Insert(entity = AccountDbEntity::class)
    suspend fun createAccount(accountDbEntity: AccountDbEntity)

    @Delete(entity = AccountDbEntity::class)
    suspend fun deleteAccount(accountDbEntity: AccountDbEntity)

    @Update(entity = AccountDbEntity::class)
    suspend fun updateUsername(account: AccountUpdateUsernameTuple)

    @Update(entity = AccountDbEntity::class)
    suspend fun updatePassword(account: AccountUpdatePasswordTuple)

    @Update(entity = AccountDbEntity::class)
    suspend fun updateFullName(account: AccountUpdateFullNameTuple)

    @Query("SELECT id, hash, salt FROM accounts WHERE email = :email")
    suspend fun findByEmail(email: String): AccountSignInTuple?

    @Query("SELECT id, hash, salt FROM accounts WHERE username = :username")
    suspend fun findByUsername(username: String): AccountSignInTuple?

    @Transaction
    suspend fun resetAccountData(accountId: Long) {
        deleteWordsProgress(accountId)
        deleteWordSetsProgress(accountId)
        deleteAchievementsProgress(accountId)
    }

    @Query("DELETE FROM accounts_words_progress WHERE account_id = :accountId")
    suspend fun deleteWordsProgress(accountId: Long)

    @Query("DELETE FROM accounts_word_sets WHERE account_id = :accountId")
    suspend fun deleteWordSetsProgress(accountId: Long)

    @Query("DELETE FROM accounts_achievements_progress WHERE account_id = :accountId")
    suspend fun deleteAchievementsProgress(accountId: Long)


}