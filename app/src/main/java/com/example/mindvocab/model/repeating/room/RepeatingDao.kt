package com.example.mindvocab.model.repeating.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.mindvocab.model.repeating.room.entities.RepeatingWordDetailTuple
import com.example.mindvocab.model.repeating.room.entities.RepeatingWordTuple
import com.example.mindvocab.model.repeating.room.entities.UpdateWordProgressAsForgottenTuple
import com.example.mindvocab.model.repeating.room.entities.UpdateWordProgressAsRememberedTuple
import com.example.mindvocab.model.word.room.entities.AccountWordProgressDbEntity

@Dao
interface RepeatingDao {

    @Query("SELECT\n" +
            "    words.id,\n" +
            "    words.word,\n" +
            "    words.transcription,\n" +
            "    words.explanation,\n" +
            "    IFNULL(translations.translation, \"\") AS translation,\n" +
            "    word_sets.image AS word_set_image,\n" +
            "    account_word_progress.started_at,\n" +
            "    account_word_progress.last_repeated_at,\n" +
            "    account_word_progress.times_repeated\n" +
            "FROM\n" +
            "    words\n" +
            "JOIN\n" +
            "    account_word_progress ON account_word_progress.word_id = words.id AND account_word_progress.account_id = :accountId\n" +
            "LEFT JOIN\n" +
            "    translations ON translations.word_id = words.id AND translations.language_id = :translationId\n" +
            "LEFT JOIN\n" +
            "    word_sets ON word_sets.id = words.word_set_id\n" +
            "WHERE\n" +
            "    account_word_progress.times_repeated < :timesRepeatedToLearn AND account_word_progress.started_at != 0;")
    suspend fun getAllWordsForRepeating(accountId: Long, timesRepeatedToLearn: Int, translationId: Long) : List<RepeatingWordDetailTuple>?

    @Query("SELECT\n" +
            "    words.id,\n" +
            "    words.word,\n" +
            "    words.transcription,\n" +
            "    words.explanation,\n" +
            "    IFNULL(translations.translation, \"\") AS translation,\n" +
            "    account_word_progress.times_repeated\n" +
            "FROM\n" +
            "    words\n" +
            "JOIN\n" +
            "    account_word_progress ON account_word_progress.word_id = words.id AND account_word_progress.account_id = :accountId\n" +
            "LEFT JOIN\n" +
            "    translations ON translations.word_id = words.id AND translations.language_id = :translationId\n" +
            "WHERE\n" +
            "    account_word_progress.times_repeated < :timesRepeatedToLearn AND account_word_progress.started_at != 0 AND last_repeated_at < :todayInMillis\n" +
            "LIMIT 1;")
    suspend fun getWordForRepeating(accountId: Long, timesRepeatedToLearn: Int, translationId: Long, todayInMillis: Long) : RepeatingWordTuple?

    @Update(entity = AccountWordProgressDbEntity::class)
    suspend fun updateWordProgressAsRemembered(accountWordProgress: UpdateWordProgressAsRememberedTuple)

    @Update(entity = AccountWordProgressDbEntity::class)
    suspend fun updateWordProgressAsForgotten(accountWordProgress: UpdateWordProgressAsForgottenTuple)

}