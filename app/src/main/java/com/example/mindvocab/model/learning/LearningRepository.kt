package com.example.mindvocab.model.learning

import com.example.mindvocab.model.AuthException
import com.example.mindvocab.model.NoWordsToLearnException
import com.example.mindvocab.model.Repository
import com.example.mindvocab.model.word.entities.Word
import kotlinx.coroutines.flow.Flow

interface LearningRepository : Repository {

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

}