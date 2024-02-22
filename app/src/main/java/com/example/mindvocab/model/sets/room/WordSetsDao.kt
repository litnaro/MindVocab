package com.example.mindvocab.model.sets.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.mindvocab.model.sets.room.entity.AccountWordSetDbEntity
import com.example.mindvocab.model.sets.room.entity.WordSetDbEntity
import com.example.mindvocab.model.sets.room.entity.WordSetsWithStatisticDbView
import kotlinx.coroutines.flow.Flow

@Dao
interface WordSetsDao {

    @Query("SELECT * FROM word_sets_statistic WHERE account_id = :accountId")
    fun getWordSets(accountId: Long) : Flow<List<WordSetsWithStatisticDbView>>

    @Upsert
    suspend fun setSelectedFlagForWordSet(accountWordSetSelection: AccountWordSetDbEntity)

    //TODO: decide about strategy

    @Update
    suspend fun updateWordSet(wordSetDbEntity: WordSetDbEntity)

    @Insert
    suspend fun createWordSet(wordSetDbEntity: WordSetDbEntity)

    @Delete
    suspend fun removeWordSet(wordSetDbEntity: WordSetDbEntity)
}