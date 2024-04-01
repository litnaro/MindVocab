package com.example.mindvocab.model.learning.room

import com.example.mindvocab.model.AuthException
import com.example.mindvocab.model.NoMoreWordsToLearnForTodayException
import com.example.mindvocab.model.NoWordsToLearnException
import com.example.mindvocab.model.account.AccountsRepository
import com.example.mindvocab.model.achievement.AchievementsRepository
import com.example.mindvocab.model.learning.LearningRepository
import com.example.mindvocab.model.learning.room.entities.UpdateWordProgressAsLearningTuple
import com.example.mindvocab.model.room.wrapSQLiteException
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
import java.util.Calendar
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

    override suspend fun getWordToLearn() = wrapSQLiteException(ioDispatcher) {
        accountsRepository.getAccount().collect { account ->
            if (account == null) throw AuthException()
            if (getTodayStartedWordsCount().first() >= learningSettings.wordsADaySetting.first().value) throw NoMoreWordsToLearnForTodayException()
            val wordTuple = learningDao.getWordToLearn(account.id) ?: throw NoWordsToLearnException()
            val languageSetting = applicationSettings.getApplicationNativeLanguage()
            currentWordToLearn.emit(wordTuple.toWord(languageSetting))
            updateIsPreviousWordAvailable()
        }
    }

    override suspend fun onWordKnown(word: Word) = wrapSQLiteException(ioDispatcher) {
        accountsRepository.getAccount().collect { account ->
            if (account == null) throw AuthException()

            // lastRepeatedAt and startedAt should be the same
            // because that's the only way to differentiate KNOWN word from LEARNED
            val currentTimeMillis = System.currentTimeMillis()
            learningDao.updateWordProgressAsKnown(
                AccountWordProgressDbEntity(
                    accountId = account.id,
                    wordId = word.id,
                    timesRepeated = WordsCalculations.TIMES_REPEATED_TO_LEARN.toByte(),
                    lastRepeatedAt = currentTimeMillis,
                    startedAt = currentTimeMillis
                )
            )
            achievementsRepository.increaseAchievementsProgressByAction(AchievementsRepository.AchievementAction.WORD_KNOWN_ACTION)
            previousWordsDequeue.add(word)
            getWordToLearn()
        }
    }

    override suspend fun onWordToLearn(word: Word) = wrapSQLiteException(ioDispatcher) {
        accountsRepository.getAccount().collect { account ->
            if (account == null) throw AuthException()
            learningDao.updateWordProgressAsLearning(
                UpdateWordProgressAsLearningTuple(
                    accountId = account.id,
                    wordId = word.id,
                    startedAt = Calendar.getInstance().timeInMillis
                )
            )
            previousWordsDequeue.add(word)
            getWordToLearn()
        }
    }

    override suspend fun getTodayStartedWordsCount(): Flow<Int> = wrapSQLiteException(ioDispatcher){
        val account = accountsRepository.getAccount().first() ?: throw AuthException()
        learningDao.getStartedWordsCount(
            accountId = account.id,
            timesRepeatedToLearn = WordsCalculations.TIMES_REPEATED_TO_LEARN,
            todayInMillis = TimeCalculations.getStartOfTodayInMillis()
        ).map {
            it.startedWordsCount
        }
    }

    override suspend fun returnPreviousWord() = wrapSQLiteException(ioDispatcher) {
        if (previousWordsDequeue.isNotEmpty()) {
            val account = accountsRepository.getAccount().first() ?: throw AuthException()
            val word = previousWordsDequeue.removeLast()
            learningDao.resetWordProgress(
                AccountWordProgressDbEntity(
                    accountId = account.id,
                    wordId = word.id,
                    lastRepeatedAt = 0,
                    startedAt = 0,
                    timesRepeated = 0
                )
            )
            achievementsRepository.decreaseAchievementsProgressByAction(AchievementsRepository.AchievementAction.WORD_KNOWN_ACTION)
            currentWordToLearn.emit(word)
            updateIsPreviousWordAvailable()
        }
    }

    private suspend fun updateIsPreviousWordAvailable() {
        isPreviousWordAvailable.emit(previousWordsDequeue.isNotEmpty())
    }

}