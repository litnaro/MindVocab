package com.example.mindvocab.model.learning.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.mindvocab.model.learning.room.entities.UpdateWordProgressAsLearningTuple
import com.example.mindvocab.model.learning.room.entities.WordForLearningTuple
import com.example.mindvocab.model.word.room.entities.AccountWordProgressDbEntity

@Dao
interface LearningDao {

    @Transaction
    @Query("SELECT\n" +
            "    words.id,\n" +
            "    words.word,\n" +
            "    words.image,\n" +
            "    words.audio,\n" +
            "    words.transcription,\n" +
            "    words.explanation,\n" +
            "    words.word_set_id\n" +
            "FROM\n" +
            "    words\n" +
            "LEFT JOIN\n" +
            "    word_sets ON words.word_set_id = word_sets.id\n" +
            "LEFT JOIN\n" +
            "    account_word_set ON account_word_set.word_set_id = word_sets.id AND account_word_set.account_id = :accountId\n" +
            "LEFT JOIN\n" +
            "    account_word_progress ON account_word_progress.word_id = words.id AND account_word_progress.account_id = :accountId\n" +
            "WHERE\n" +
            "    account_word_set.is_selected = 1 AND account_word_progress.started_at = 0\n" +
            "GROUP BY\n" +
            "    words.id\n" +
            "LIMIT 1;")
    suspend fun getWordToLearn(accountId: Long): WordForLearningTuple?

    @Update(entity = AccountWordProgressDbEntity::class)
    suspend fun updateWordProgressAsLearning(accountWordProgress: UpdateWordProgressAsLearningTuple)

    @Update(entity = AccountWordProgressDbEntity::class)
    suspend fun updateWordProgressAsKnown(accountWordProgress: AccountWordProgressDbEntity)
}