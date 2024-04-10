package com.example.mindvocab.model.repeating

import com.example.mindvocab.model.AuthException
import com.example.mindvocab.model.StorageException
import com.example.mindvocab.model.Repository
import com.example.mindvocab.model.word.entities.WordToRepeat
import com.example.mindvocab.model.word.entities.WordToRepeatDetail
import kotlinx.coroutines.flow.Flow

interface RepeatingRepository : Repository {

    /**
     * Subscribe on word to repeat.
     * Not auto-updated. Updates only after calling getWordsToRepeat() method.
     * @return All base word fields and how many times account has already repeated this word.
     */
    suspend fun listenWordToRepeat() : Flow<WordToRepeat>

    /**
     * Emits new value to all listenWordToRepeat() flow subscribers.
     * Update with only words witch were last time repeated before today or with zero value (only started to learn words).
     * Needs in case of fragment lifecycle problems and for the first value initiation.
     * ApplicationSettings.NativeLanguageSetting - effect on translation language.
     * @throws AuthException
     * @throws NoWordsToRepeatException
     * @throws WordsToRepeatCurrentlyInTimeout
     * @throws StorageException
     */
    suspend fun getWordToRepeat()

    /**
     * Gets list of the words to repeat.
     * If word started_at not null ro empty, that word will be put to list.
     * @throws AuthException
     * @throws StorageException
     * @return Flow of words with progress metadata and details.
     */
    suspend fun getWordsToRepeat() : Flow<List<WordToRepeatDetail>>

    /**
     * Updates word progress by increasing its progress.
     * Also updates word`s date of last repeat to a new one.
     * Emits new value to  word to repeat subscribers after succeed operations.
     * @throws AuthException
     * @throws StorageException
     */
    suspend fun onWordRemember(word: WordToRepeat)

    /**
     * Update only word`s date of last repeat to current time.
     * Progression of the word is staying the same.
     * Emits new value to  word to repeat subscribers after succeed operations.
     * @throws AuthException
     * @throws StorageException
     */
    suspend fun onWordForgot(word: WordToRepeat)

}