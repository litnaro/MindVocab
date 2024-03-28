package com.example.mindvocab.model.learning.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.mindvocab.model.learning.room.entities.UpdateWordProgressAsLearningTuple
import com.example.mindvocab.model.learning.room.entities.WordForLearningTuple
import com.example.mindvocab.model.repeating.room.entities.StartedWordsTuple
import com.example.mindvocab.model.word.room.entities.AccountWordProgressDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LearningDao {

    @Query("SELECT\n" +
            "    words.*\n" +
            "FROM\n" +
            "    words\n" +
            "LEFT JOIN\n" +
            "    word_sets ON words.word_set_id = word_sets.id\n" +
            "LEFT JOIN\n" +
            "    accounts_word_sets ON accounts_word_sets.word_set_id = word_sets.id AND accounts_word_sets.account_id = :accountId\n" +
            "LEFT JOIN\n" +
            "    accounts_words_progress ON accounts_words_progress.word_id = words.id AND accounts_words_progress.account_id = :accountId\n" +
            "WHERE\n" +
            "    accounts_word_sets.is_selected = 1 AND accounts_words_progress.started_at = 0\n" +
            "GROUP BY\n" +
            "    RANDOM()\n" +
            "LIMIT 1;")
    suspend fun getWordToLearn(accountId: Long): WordForLearningTuple?

    @Query("SELECT\n" +
            "   count(*) as started_words_count\n" +
            "FROM\n" +
            "   accounts_words_progress\n" +
            "WHERE\n" +
            "    accounts_words_progress.account_id = :accountId " +
            "       AND accounts_words_progress.started_at > :todayInMillis" +
            "       AND accounts_words_progress.times_repeated != :timesRepeatedToLearn")
    fun getStartedWordsCount(accountId: Long, timesRepeatedToLearn: Int, todayInMillis: Long) : Flow<StartedWordsTuple>

    @Update(entity = AccountWordProgressDbEntity::class)
    suspend fun updateWordProgressAsLearning(accountWordProgress: UpdateWordProgressAsLearningTuple)

    @Update(entity = AccountWordProgressDbEntity::class)
    suspend fun updateWordProgressAsKnown(accountWordProgress: AccountWordProgressDbEntity)

    @Update(entity = AccountWordProgressDbEntity::class)
    suspend fun resetWordProgress(accountWordProgress: AccountWordProgressDbEntity)

}