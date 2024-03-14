package com.example.mindvocab.model.repeating.room

import com.example.mindvocab.model.AuthException
import com.example.mindvocab.model.NoWordsToRepeatException
import com.example.mindvocab.model.account.AccountsRepository
import com.example.mindvocab.model.achievement.AchievementsRepository
import com.example.mindvocab.model.repeating.RepeatingRepository
import com.example.mindvocab.model.repeating.room.entities.UpdateWordProgressAsForgottenTuple
import com.example.mindvocab.model.repeating.room.entities.UpdateWordProgressAsRememberedTuple
import com.example.mindvocab.model.settings.application.ApplicationSettings
import com.example.mindvocab.model.word.WordsCalculations
import com.example.mindvocab.model.word.entities.WordToRepeat
import com.example.mindvocab.model.word.entities.WordToRepeatDetail
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomRepeatingRepository @Inject constructor(
    private val repeatingDao: RepeatingDao,
    private val accountsRepository: AccountsRepository,
    private val applicationSettings: ApplicationSettings,
    private val achievementsRepository: AchievementsRepository,
    private val ioDispatcher: CoroutineDispatcher
) : RepeatingRepository {

    private val currentWordToRepeat = MutableSharedFlow<WordToRepeat>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override suspend fun getWordsToRepeat(): Flow<List<WordToRepeatDetail>> {
        val account = accountsRepository.getAccount().first() ?: throw AuthException()
        val translation = applicationSettings.getApplicationNativeLanguage()
        val list = repeatingDao.getAllWordsForRepeating(
            accountId = account.id,
            timesRepeatedToLearn = WordsCalculations.getWordTimesRepeatedToLearn(),
            translationId = translation.value.toLong()
        ) ?: throw NoWordsToRepeatException()
        return flowOf(list.map { it.toRepeatingWordDetail() })
    }

    override suspend fun listenWordToRepeat(): Flow<WordToRepeat> {
        return currentWordToRepeat
    }

    override suspend fun getWordToRepeat() = withContext(ioDispatcher) {
        val translation = applicationSettings.getApplicationNativeLanguage()
        accountsRepository.getAccount().collect { account ->
            if (account == null) throw AuthException()
            val wordToRepeat = repeatingDao.getWordForRepeating(
                accountId = account.id,
                timesRepeatedToLearn = WordsCalculations.getWordTimesRepeatedToLearn(),
                translationId = translation.value.toLong(),
                todayInMillis = WordsCalculations.getStartOfTodayInMillis()
            ) ?: throw NoWordsToRepeatException()
            currentWordToRepeat.emit(wordToRepeat.toWordToRepeat())
        }
    }

    override suspend fun onWordRemember(word: WordToRepeat) = withContext(ioDispatcher) {
        accountsRepository.getAccount().collect { account ->
            if (account == null) throw AuthException()
            repeatingDao.updateWordProgressAsRemembered(
                UpdateWordProgressAsRememberedTuple(
                    accountId = account.id,
                    wordId = word.id,
                    timesRepeated = (word.timesRepeated + 1).toByte(),
                    lastRepeatedAt = System.currentTimeMillis()
                )
            )
            if (word.timesRepeated + 1 == WordsCalculations.getWordTimesRepeatedToLearn()) {
                achievementsRepository.updateAchievementsByAction(AchievementsRepository.AchievementAction.WORD_LEARN_ACTION)
            }
            getWordToRepeat()
        }
    }

    override suspend fun onWordForgot(word: WordToRepeat) = withContext(ioDispatcher) {
        accountsRepository.getAccount().collect { account ->
            if (account == null) throw AuthException()
            repeatingDao.updateWordProgressAsForgotten(
                UpdateWordProgressAsForgottenTuple(
                    accountId = account.id,
                    wordId = word.id,
                    lastRepeatedAt = System.currentTimeMillis()
                )
            )
            getWordToRepeat()
        }
    }

}