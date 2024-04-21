package com.example.mindvocab.model.repeating.room

import com.example.mindvocab.model.AuthException
import com.example.mindvocab.model.repeating.NoWordsToRepeatException
import com.example.mindvocab.model.account.AccountsRepository
import com.example.mindvocab.model.achievement.AchievementsRepository
import com.example.mindvocab.model.repeating.RepeatingRepository
import com.example.mindvocab.model.repeating.room.entities.UpdateWordProgressAsForgottenTuple
import com.example.mindvocab.model.repeating.room.entities.UpdateWordProgressAsRememberedTuple
import com.example.mindvocab.model.room.wrapSQLiteException
import com.example.mindvocab.model.settings.account.AccountSettings
import com.example.mindvocab.model.settings.application.ApplicationSettings
import com.example.mindvocab.model.word.TimeCalculations
import com.example.mindvocab.model.word.WordsCalculations
import com.example.mindvocab.model.word.entities.WordToRepeat
import com.example.mindvocab.model.word.entities.WordToRepeatDetail
import com.example.mindvocab.model.word.room.entities.WordRepeatLogDbEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomRepeatingRepository @Inject constructor(
    private val repeatingDao: RepeatingDao,
    private val accountsRepository: AccountsRepository,
    private val accountSettings: AccountSettings,
    private val applicationSettings: ApplicationSettings,
    private val achievementsRepository: AchievementsRepository,
    private val ioDispatcher: CoroutineDispatcher
) : RepeatingRepository {

    private val currentWordToRepeat = MutableSharedFlow<WordToRepeat>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override fun listenWordToRepeat(): Flow<WordToRepeat> {
        return currentWordToRepeat
    }

    override fun getWordsToRepeat(): Flow<List<WordToRepeatDetail>> {
        val accountId = accountSettings.getCurrentAccountId()
        if (accountId == AccountSettings.NO_ACCOUNT_ID) throw AuthException()

        val translation = applicationSettings.nativeLanguage.asStateFlow()
        val listOfRepeatingWords = repeatingDao.getAllWordsForRepeating(
                accountId = accountId,
                timesRepeatedToLearn = WordsCalculations.TIMES_REPEATED_TO_LEARN,
                translationId = translation.value.value.toLong()
        )

        return listOfRepeatingWords.map {  entities ->
            if (entities == null) throw NoWordsToRepeatException()
            entities.map {
                it.toRepeatingWordDetail()
            }
        }
    }

    override suspend fun getWordToRepeat() = wrapSQLiteException(ioDispatcher) {
        accountsRepository.getAccount().collect { account ->
            if (account == null) throw AuthException()
            val translation = applicationSettings.nativeLanguage.first()
            val wordToRepeat = repeatingDao.getWordForRepeating(
                    accountId = account.id,
                    timesRepeatedToLearn = WordsCalculations.TIMES_REPEATED_TO_LEARN,
                    translationId = translation.value.toLong(),
                    todayInMillis = TimeCalculations.getStartOfTodayInMillis()
            ) ?: throw NoWordsToRepeatException()
            currentWordToRepeat.emit(wordToRepeat.toWordToRepeat())
        }
    }

    override suspend fun onWordRemember(word: WordToRepeat) = wrapSQLiteException(ioDispatcher) {
        accountsRepository.getAccount().collect { account ->
            if (account == null) throw AuthException()
            val currentTime = System.currentTimeMillis()
            repeatingDao.updateWordProgressAsRemembered(
                UpdateWordProgressAsRememberedTuple(
                    accountId = account.id,
                    wordId = word.id,
                    timesRepeated = (word.timesRepeated + 1).toByte(),
                    lastRepeatedAt = currentTime
                )
            )
            repeatingDao.logWordRepeatAction(
                WordRepeatLogDbEntity(
                    id = 0,
                    accountId = account.id,
                    wordId = word.id,
                    repeatDate = currentTime
                )
            )
            if (word.timesRepeated + 1 == WordsCalculations.TIMES_REPEATED_TO_LEARN) {
                achievementsRepository.increaseAchievementsProgressByAction(AchievementsRepository.AchievementAction.WORD_LEARN_ACTION)
            }
            getWordToRepeat()
        }
    }

    override suspend fun onWordForgot(word: WordToRepeat) = wrapSQLiteException(ioDispatcher) {
        accountsRepository.getAccount().collect { account ->
            if (account == null) throw AuthException()
            val currentTime = System.currentTimeMillis()
            repeatingDao.updateWordProgressAsForgotten(
                UpdateWordProgressAsForgottenTuple(
                    accountId = account.id,
                    wordId = word.id,
                    lastRepeatedAt = currentTime
                )
            )
            repeatingDao.logWordRepeatAction(
                WordRepeatLogDbEntity(
                    id = 0,
                    accountId = account.id,
                    wordId = word.id,
                    repeatDate = currentTime
                )
            )
            getWordToRepeat()
        }
    }

}