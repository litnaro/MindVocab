package com.example.mindvocab.model.learning.room

import com.example.mindvocab.model.AuthException
import com.example.mindvocab.model.NoWordsToLearnException
import com.example.mindvocab.model.account.AccountsRepository
import com.example.mindvocab.model.learning.LearningRepository
import com.example.mindvocab.model.learning.room.entities.UpdateWordProgressAsLearningTuple
import com.example.mindvocab.model.settings.application.ApplicationSettings
import com.example.mindvocab.model.word.WordCalculations
import com.example.mindvocab.model.word.entities.Word
import com.example.mindvocab.model.word.room.entities.AccountWordProgressDbEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.withContext

class RoomLearningRepository(
    private val learningDao: LearningDao,
    private val accountsRepository: AccountsRepository,
    private val applicationSettings: ApplicationSettings,
    private val ioDispatcher: CoroutineDispatcher
) : LearningRepository {

    private val currentWordToLearn = MutableSharedFlow<Word>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override suspend fun listenWordToLearn(): Flow<Word> {
        return currentWordToLearn
    }

    override suspend fun getWordToLearn() = withContext(ioDispatcher) {
        //TODO live updates with native language settings. Now works only with application restart
        accountsRepository.getAccount().collect { account ->
            if (account == null) throw AuthException()
            val wordTuple = learningDao.getWordToLearn(account.id) ?: throw NoWordsToLearnException()
            val languageSetting = applicationSettings.getApplicationNativeLanguage()
            currentWordToLearn.emit(wordTuple.toWord(languageSetting))
        }
    }

    override suspend fun onWordKnown(word: Word) = withContext(ioDispatcher) {
        accountsRepository.getAccount().collect { account ->
            if (account == null) throw AuthException()

            // lastRepeatedAt and startedAt should be the same
            // because that's the only way to differentiate KNOWN word from LEARNED
            val currentTimeMillis = System.currentTimeMillis()

            learningDao.updateWordProgressAsKnown(
                AccountWordProgressDbEntity(
                    accountId = account.id,
                    wordId = word.id,
                    isSelected = false,
                    timesRepeated = WordCalculations.getWordTimesRepeatedToLearn().toByte(),
                    lastRepeatedAt = currentTimeMillis,
                    startedAt = currentTimeMillis
                )
            )
            getWordToLearn()
        }
    }

    override suspend fun onWordToLearn(word: Word) = withContext(ioDispatcher) {
        accountsRepository.getAccount().collect { account ->
            if (account == null) throw AuthException()
            learningDao.updateWordProgressAsLearning(
                UpdateWordProgressAsLearningTuple(
                    accountId = account.id,
                    wordId = word.id,
                    startedAt = System.currentTimeMillis()
                )
            )
            getWordToLearn()
        }
    }

}