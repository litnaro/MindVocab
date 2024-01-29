package com.example.mindvocab.model.word.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.mindvocab.model.word.room.entities.WordDbEntity
import com.example.mindvocab.model.word.room.entities.WordWithExamplesAndTranslationsTuple
import kotlinx.coroutines.flow.Flow

@Dao
interface WordsDao {

    @Query("SELECT * FROM words WHERE word_set_id = :wordSetId")
    fun getWordsByWordSetId(wordSetId: Long): Flow<List<WordDbEntity>>

    @Transaction
    @Query("SELECT * FROM words WHERE id = 3")
    fun getWordToLearn(): Flow<WordWithExamplesAndTranslationsTuple>
}