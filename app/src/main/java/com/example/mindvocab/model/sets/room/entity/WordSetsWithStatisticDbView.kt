package com.example.mindvocab.model.sets.room.entity

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Embedded
import com.example.mindvocab.model.sets.entity.WordSet

/**
 * DatabaseView select every combination of accounts and word sets.
 * Sets whether the account has selected a word set to learn or not.
 * If account_word_set table has not record, DbView set as unselected.
 * Calculate words in each word set.
 * Calculates the number of words an account has completed for a specific word set.
 */
@DatabaseView(
    viewName = "word_sets_statistic",
    value = "SELECT\n" +
            "    accounts.id AS account_id,\n" +
            "    word_sets.id,\n" +
            "    word_sets.name,\n" +
            "    word_sets.image,\n" +
            "    IFNULL(account_word_set.is_selected, 0) AS is_selected,\n" +
            "    (SELECT COUNT(*) FROM words WHERE words.word_set_id = word_sets.id) AS words_count,\n" +
            "    (SELECT COUNT(*)\n" +
            "        FROM (\n" +
            "            SELECT words.id\n" +
            "            FROM words, account_word_progress\n" +
            "            WHERE words.word_set_id = word_sets.id\n" +
            "                AND account_word_progress.word_id = words.id\n" +
            "                AND account_word_progress.times_repeated = 6\n" +
            "                AND account_word_progress.account_id = accounts.id\n" +
            "            GROUP BY words.id)\n" +
            "    ) AS words_completed\n" +
            "FROM\n" +
            "    accounts\n" +
            "JOIN\n" +
            "    word_sets\n" +
            "LEFT JOIN\n" +
            "    account_word_set ON account_word_set.account_id = accounts.id\n" +
            "                        AND account_word_set.word_set_id = word_sets.id"
)
data class WordSetsWithStatisticDbView(
    @ColumnInfo(name = "account_id") val accountId: Long,
    @Embedded val wordSet: WordSetDbEntity,
    @ColumnInfo(name = "is_selected") val isSelected: Boolean,
    @ColumnInfo(name = "words_count") val wordsCount: Int,
    @ColumnInfo(name = "words_completed") val wordsCompleted: Int
) {
    fun toWordSet() = WordSet(
        id = wordSet.id,
        name = wordSet.name,
        image = wordSet.image,
        isSelected = isSelected,
        wordsCount = wordsCount,
        wordsCompleted = wordsCompleted
    )
}