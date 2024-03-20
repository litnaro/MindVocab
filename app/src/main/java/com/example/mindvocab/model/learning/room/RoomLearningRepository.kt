package com.example.mindvocab.model.learning.room

import com.example.mindvocab.model.AuthException
import com.example.mindvocab.model.NoMoreWordsToLearnForTodayException
import com.example.mindvocab.model.NoWordsToLearnException
import com.example.mindvocab.model.StorageException
import com.example.mindvocab.model.account.AccountsRepository
import com.example.mindvocab.model.achievement.AchievementsRepository
import com.example.mindvocab.model.learning.LearningRepository
import com.example.mindvocab.model.learning.room.entities.UpdateWordProgressAsLearningTuple
import com.example.mindvocab.model.learning.room.entities.WordForLearningTuple
import com.example.mindvocab.model.settings.application.ApplicationSettings
import com.example.mindvocab.model.settings.learn.LearningSettings
import com.example.mindvocab.model.word.TimeCalculations
import com.example.mindvocab.model.word.WordsCalculations
import com.example.mindvocab.model.word.entities.Word
import com.example.mindvocab.model.word.room.entities.AccountWordProgressDbEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomLearningRepository @Inject constructor(
    private val learningDao: LearningDao,
    private val accountsRepository: AccountsRepository,
    private val applicationSettings: ApplicationSettings,
    private val learningSettings: LearningSettings,
    private val achievementsRepository: AchievementsRepository,
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

    private val previousWordsDequeue = ArrayDeque<Word>()
    private val isPreviousWordAvailable = MutableSharedFlow<Boolean>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    override suspend fun listenIsReturnPreviousWordEnabled(): Flow<Boolean> {
        return isPreviousWordAvailable
    }

    override suspend fun getWordToLearn() = withContext(ioDispatcher) {
        accountsRepository.getAccount().collect { account ->
            if (account == null) throw AuthException()
            if (getTodayStartedWordsCount().first() >= learningSettings.wordsADaySetting.first().value) throw NoMoreWordsToLearnForTodayException()
            val wordTuple: WordForLearningTuple?
            try {
                wordTuple = learningDao.getWordToLearn(account.id)
            } catch (e: Exception) {
                throw StorageException()
            }
            if (wordTuple == null) throw NoWordsToLearnException()
            val languageSetting = applicationSettings.getApplicationNativeLanguage()
            currentWordToLearn.emit(wordTuple.toWord(languageSetting))
            updateIsPreviousWordAvailable()
        }
    }

    override suspend fun onWordKnown(word: Word) = withContext(ioDispatcher) {
        accountsRepository.getAccount().collect { account ->
            if (account == null) throw AuthException()

            // lastRepeatedAt and startedAt should be the same
            // because that's the only way to differentiate KNOWN word from LEARNED
            val currentTimeMillis = System.currentTimeMillis()
            try {
                learningDao.updateWordProgressAsKnown(
                    AccountWordProgressDbEntity(
                        accountId = account.id,
                        wordId = word.id,
                        timesRepeated = WordsCalculations.TIMES_REPEATED_TO_LEARN.toByte(),
                        lastRepeatedAt = currentTimeMillis,
                        startedAt = currentTimeMillis
                    )
                )
            } catch (e: Exception) {
                throw StorageException()
            }
            achievementsRepository.increaseAchievementsProgressByAction(AchievementsRepository.AchievementAction.WORD_KNOWN_ACTION)
            previousWordsDequeue.add(word)
            getWordToLearn()
        }
    }

    override suspend fun onWordToLearn(word: Word) = withContext(ioDispatcher) {
        accountsRepository.getAccount().collect { account ->
            if (account == null) throw AuthException()
            try {
                learningDao.updateWordProgressAsLearning(
                    UpdateWordProgressAsLearningTuple(
                        accountId = account.id,
                        wordId = word.id,
                        startedAt = System.currentTimeMillis()
                    )
                )
            } catch (e: Exception) {
                throw StorageException()
            }
            previousWordsDequeue.add(word)
            getWordToLearn()
        }
    }

    override suspend fun getTodayStartedWordsCount(): Flow<Int> = withContext(ioDispatcher){
        val account = accountsRepository.getAccount().first() ?: throw AuthException()
        try {
            learningDao.getStartedWordsCount(
                accountId = account.id,
                timesRepeatedToLearn = WordsCalculations.TIMES_REPEATED_TO_LEARN,
                todayInMillis = TimeCalculations.getStartOfTodayInMillis()
            ).map {
                it.startedWordsCount
            }
        } catch (e: Exception) {
            throw StorageException()
        }
    }

    override suspend fun returnPreviousWord() = withContext(ioDispatcher) {
        if (previousWordsDequeue.isNotEmpty()) {
            val account = accountsRepository.getAccount().first() ?: throw AuthException()
            val word = previousWordsDequeue.removeLast()
            try {
                learningDao.resetWordProgress(
                    AccountWordProgressDbEntity(
                        accountId = account.id,
                        wordId = word.id,
                        lastRepeatedAt = 0,
                        startedAt = 0,
                        timesRepeated = 0
                    )
                )
            } catch (e: Exception) {
                throw StorageException()
            }
            achievementsRepository.decreaseAchievementsProgressByAction(AchievementsRepository.AchievementAction.WORD_KNOWN_ACTION)
            currentWordToLearn.emit(word)
            updateIsPreviousWordAvailable()
        }
    }

    private suspend fun updateIsPreviousWordAvailable() {
        isPreviousWordAvailable.emit(previousWordsDequeue.isNotEmpty())
    }

}