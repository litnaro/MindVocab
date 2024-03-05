package com.example.mindvocab.model.learning

import com.example.mindvocab.model.AuthException
import com.example.mindvocab.model.NoWordsToLearnException
import com.example.mindvocab.model.NoMoreWordsToLearnForTodayException
import com.example.mindvocab.model.Repository
import com.example.mindvocab.model.word.entities.Word
import kotlinx.coroutines.flow.Flow

interface LearningRepository : Repository {

    //TODO add storage exception

    /**
     * Subscribe on word to learn.
     * Not auto-updated. Updates only after calling getWordToLearn() method.
     * @return All possible information about word for learning.
     */
    suspend fun listenWordToLearn() : Flow<Word>

    /**
     * Get all information about word to learn.
     * Emits new value to all listenWordToLearn() flow subscribers.
     * Requires ApplicationSettings.NativeLanguageSetting to decide which translation get from database.
     * @throws AuthException Cannot get words progress without account information.
     * @throws NoWordsToLearnException In case if all words already learned or none of the word set as selected.
     * @throws NoMoreWordsToLearnForTodayException Throws when more then LearningSettings.WordsADay words were marked as started today.
     */
    suspend fun getWordToLearn()

    /**
     * Update word progress for specific account.
     * Sets word repeat count by user to TIMES_REPEATED_TO_LEARN constant, to skip it from learning.
     * Sets started and last repeated time metadata to current time.
     * Important to set the same value to started and repeated fields, because that's the only way to differentiate KNOWN word from LEARNED.
     * After updates - load new word to learn.
     * @param word Current word from learning screen.
     * @throws AuthException Cannot update word progress without account information.
     */
    suspend fun onWordKnown(word: Word)

    /**
     * Sets word startedAt time to current time.
     * After updates - load new word to learn.
     * @param word Current word from learning screen.
     * @throws AuthException Cannot update word progress without account information.
     */
    suspend fun onWordToLearn(word: Word)

    /**
     * Calculate words witch were started today.
     * So user could control how many new words a day he should learn.
     * Word is started when it's started_at value more then today's date and times repeated less then needed to learn.
     * Last repeated date not involved because after word started, it can be repeated at the same day, so it could be infinite learning words.
     * @throws AuthException Cannot update word progress without account information.
     * @return Flow of how many words were learned today.
     */
    suspend fun getTodayStartedWordsCount() : Flow<Int>

}