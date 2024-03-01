package com.example.mindvocab.model.sets.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.example.mindvocab.model.sets.room.entity.AccountWordSetDbEntity
import com.example.mindvocab.model.sets.room.entity.WordSetDbEntity
import com.example.mindvocab.model.sets.room.entity.WordSetsWithStatisticDbView
import com.example.mindvocab.model.word.room.entities.AccountWordProgressDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WordSetsDao {

    @Query("SELECT * FROM word_sets_statistic WHERE account_id = :accountId")
    fun getWordSets(accountId: Long) : Flow<List<WordSetsWithStatisticDbView>>

    @Upsert
    suspend fun upsertWordSetSelection(accountWordSetSelection: AccountWordSetDbEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWordsToAccountProgress(words: List<AccountWordProgressDbEntity>)

    @Transaction
    suspend fun upsertWordSetSelection(accountWordSetSelection: AccountWordSetDbEntity, words: List<AccountWordProgressDbEntity>) {
        upsertWordSetSelection(accountWordSetSelection)
        addWordsToAccountProgress(words)
    }

    @Update
    suspend fun updateWordSet(wordSetDbEntity: WordSetDbEntity)

    @Insert
    suspend fun createWordSet(wordSetDbEntity: WordSetDbEntity)

    @Delete
    suspend fun removeWordSet(wordSetDbEntity: WordSetDbEntity)
}