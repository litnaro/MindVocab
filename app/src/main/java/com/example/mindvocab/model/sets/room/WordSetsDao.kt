package com.example.mindvocab.model.sets.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.mindvocab.model.sets.room.entity.WordSetDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WordSetsDao {

    @Query("SELECT * FROM word_sets")
    fun getWordSets() : Flow<List<WordSetDbEntity>>

    //TODO: decide about strategy

    @Update
    suspend fun updateWordSet(wordSetDbEntity: WordSetDbEntity)

    @Insert
    suspend fun createWordSet(wordSetDbEntity: WordSetDbEntity)

    @Delete
    suspend fun removeWordSet(wordSetDbEntity: WordSetDbEntity)

}