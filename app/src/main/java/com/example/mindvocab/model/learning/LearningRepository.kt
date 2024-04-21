package com.example.mindvocab.model.learning

import com.example.mindvocab.model.AuthException
import com.example.mindvocab.model.StorageException
import com.example.mindvocab.model.Repository
import com.example.mindvocab.model.word.entities.Word
import kotlinx.coroutines.flow.Flow

interface LearningRepository : Repository {

    /**
     * Subscribe on word to learn.
     * Not auto-updated. Updates only after calling getWordToLearn() method.
     * @return Flow of all possible information about word for learning.
     */
    fun listenWordToLearn() : Flow<Word>

    /**
     *  Subscribe on is return action available.
     *  Not auto-updated.
     *  @return Flow of is there previous words in the stack or not.
     */
    fun listenIsReturnPreviousWordEnabled() : Flow<Boolean>

    /**
     * Get all information about word to learn.
     * Emits new value to all listenWordToLearn() flow subscribers.
     * Requires ApplicationSettings.NativeLanguageSetting to decide which translation get from database.
     * Updates listenIsReturnPreviousWordEnabled().
     * @throws AuthException
     * @throws NoWordsToLearnException
     * @throws NoMoreWordsToLearnForTodayException
     * @throws StorageException
     */
    suspend fun getWordToLearn()

    /**
     * Update word progress for specific account.
     * Sets word repeat count by user to TIMES_REPEATED_TO_LEARN constant, to skip it from learning.
     * Sets started and last repeated time metadata to current time.
     * Important to set the same value to started and repeated fields, because that's the only way to differentiate KNOWN word from LEARNED.
     * Write word to stack so it could be returned later.
     * After updates - load new word to learn, update all achievements with "word known" action.
     * @param word Current word from learning screen.
     * @throws AuthException
     * @throws StorageException
     */
    suspend fun onWordKnown(word: Word)

    /**
     * Sets word startedAt time to current time.
     * Write word to stack so it could be returned later.
     * After updates - load new word to learn, update all achievements with "word to learn" action.
     * @param word Current word from learning screen.
     * @throws AuthException
     * @throws StorageException
     */
    suspend fun onWordToLearn(word: Word)

    /**
     * Calculate words witch were started today.
     * So user could control how many new words a day he should learn.
     * Word is started when it's started_at value more then today's date and times repeated less then needed to learn.
     * Last repeated date not involved because after word started, it can be repeated at the same day, so it could be infinite learning words.
     * @throws AuthException
     * @throws StorageException
     * @return Flow of how many words were learned today.
     */
    fun getTodayStartedWordsCount() : Flow<Int>

    /**
     * Return previous word.
     * Reset all it is progress.
     * Update listenIsReturnPreviousWordEnabled() value.
     * If no words to return - does nothing.
     * @throws AuthException
     * @throws StorageException
     */
    suspend fun returnPreviousWord()

}