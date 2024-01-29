package com.example.mindvocab.model.account.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mindvocab.model.account.room.entities.AccountDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountsDao {

    @Query("SELECT * FROM accounts WHERE id = :accountId")
    fun getAccountById(accountId: Long) : Flow<AccountDbEntity>

    @Insert(entity = AccountDbEntity::class)
    suspend fun createAccount(accountDbEntity: AccountDbEntity)

    @Delete(entity = AccountDbEntity::class)
    suspend fun deleteAccount(accountDbEntity: AccountDbEntity)

//    //TODO add tuple
//    @Update(entity = AccountDbEntity::class)
//    suspend fun updateUsername()
//
//    //TODO add tuple
//    @Update(entity = AccountDbEntity::class)
//    suspend fun changePassword()
//
//    //TODO add tuple
//    @Update(entity = AccountDbEntity::class)
//    suspend fun updateName()
//
//    //TODO add tuple
//    @Update(entity = AccountDbEntity::class)
//    suspend fun updateSurname()
//
//    //TODO add tuple
//    @Update(entity = AccountDbEntity::class)
//    suspend fun updatePhoto()
}