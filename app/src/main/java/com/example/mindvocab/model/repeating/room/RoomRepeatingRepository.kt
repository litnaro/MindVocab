package com.example.mindvocab.model.repeating.room

import com.example.mindvocab.model.AuthException
import com.example.mindvocab.model.NoWordsToRepeatException
import com.example.mindvocab.model.StorageException
import com.example.mindvocab.model.account.AccountsRepository
import com.example.mindvocab.model.achievement.AchievementsRepository
import com.example.mindvocab.model.repeating.RepeatingRepository
import com.example.mindvocab.model.repeating.room.entities.RepeatingWordDetailTuple
import com.example.mindvocab.model.repeating.room.entities.RepeatingWordTuple
import com.example.mindvocab.model.repeating.room.entities.UpdateWordProgressAsForgottenTuple
import com.example.mindvocab.model.repeating.room.entities.UpdateWordProgressAsRememberedTuple
import com.example.mindvocab.model.settings.application.ApplicationSettings
import com.example.mindvocab.model.word.TimeCalculations
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

    override suspend fun listenWordToRepeat(): Flow<WordToRepeat> {
        return currentWordToRepeat
    }

    override suspend fun getWordsToRepeat(): Flow<List<WordToRepeatDetail>> {
        val account = accountsRepository.getAccount().first() ?: throw AuthException()
        val translation = applicationSettings.getApplicationNativeLanguage()
        val listOfRepeatingWords: List<RepeatingWordDetailTuple>?
        try {
            listOfRepeatingWords = repeatingDao.getAllWordsForRepeating(
                accountId = account.id,
                timesRepeatedToLearn = WordsCalculations.TIMES_REPEATED_TO_LEARN,
                translationId = translation.value.toLong()
            )
        } catch (e: Exception) {
            throw StorageException()
        }
        if (listOfRepeatingWords == null) throw NoWordsToRepeatException()
        return flowOf(listOfRepeatingWords.map { it.toRepeatingWordDetail() })
    }

    override suspend fun getWordToRepeat() = withContext(ioDispatcher) {
        accountsRepository.getAccount().collect { account ->
            if (account == null) throw AuthException()
            val translation = applicationSettings.getApplicationNativeLanguage()
            val wordToRepeat: RepeatingWordTuple?
            try {
                wordToRepeat = repeatingDao.getWordForRepeating(
                    accountId = account.id,
                    timesRepeatedToLearn = WordsCalculations.TIMES_REPEATED_TO_LEARN,
                    translationId = translation.value.toLong(),
                    todayInMillis = TimeCalculations.getStartOfTodayInMillis()
                )
            } catch (e: Exception) {
                throw StorageException()
            }
            if (wordToRepeat == null) throw NoWordsToRepeatException()
            currentWordToRepeat.emit(wordToRepeat.toWordToRepeat())
        }
    }

    override suspend fun onWordRemember(word: WordToRepeat) = withContext(ioDispatcher) {
        accountsRepository.getAccount().collect { account ->
            if (account == null) throw AuthException()
            try {
                repeatingDao.updateWordProgressAsRemembered(
                    UpdateWordProgressAsRememberedTuple(
                        accountId = account.id,
                        wordId = word.id,
                        timesRepeated = (word.timesRepeated + 1).toByte(),
                        lastRepeatedAt = System.currentTimeMillis()
                    )
                )
            } catch (e: Exception) {
                throw StorageException()
            }
            if (word.timesRepeated + 1 == WordsCalculations.TIMES_REPEATED_TO_LEARN) {
                achievementsRepository.increaseAchievementsProgressByAction(AchievementsRepository.AchievementAction.WORD_LEARN_ACTION)
            }
            getWordToRepeat()
        }
    }

    override suspend fun onWordForgot(word: WordToRepeat) = withContext(ioDispatcher) {
        accountsRepository.getAccount().collect { account ->
            if (account == null) throw AuthException()
            try {
                repeatingDao.updateWordProgressAsForgotten(
                    UpdateWordProgressAsForgottenTuple(
                        accountId = account.id,
                        wordId = word.id,
                        lastRepeatedAt = System.currentTimeMillis()
                    )
                )
            } catch (e: Exception) {
                throw StorageException()
            }
            getWordToRepeat()
        }
    }

}