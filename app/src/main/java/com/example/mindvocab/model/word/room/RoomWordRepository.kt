package com.example.mindvocab.model.word.room

import com.example.mindvocab.model.AuthException
import com.example.mindvocab.model.NoWordsToLearnException
import com.example.mindvocab.model.account.AccountsRepository
import com.example.mindvocab.model.settings.application.ApplicationSettings
import com.example.mindvocab.model.word.WordCalculations
import com.example.mindvocab.model.word.WordRepository
import com.example.mindvocab.model.word.entities.Word
import com.example.mindvocab.model.word.entities.WordStatistic
import com.example.mindvocab.model.word.room.entities.AccountWordProgressDbEntity
import com.example.mindvocab.model.word.room.entities.UpdateWordProgressAsLearningTuple
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class RoomWordRepository(
    private val wordsDao: WordsDao,
    private val accountsRepository: AccountsRepository,
    private val applicationSettings: ApplicationSettings
) : WordRepository {

    private val currentWordToLearn = MutableSharedFlow<Word>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override suspend fun listenWordToLearn(): Flow<Word> {
        return currentWordToLearn
    }

    override suspend fun getWordsByWordSetId(wordSetId: Long): Flow<List<WordStatistic>> {
        return accountsRepository.getAccount()
            .flatMapLatest { account ->
                if (account == null) return@flatMapLatest flowOf(emptyList())
                queryWordsWithStatisticByWordSetId(wordSetId ,account.id)
            }
    }

    override suspend fun getWordToLearn() {
        //TODO live updates with native language settings. Now works only with application restart
        accountsRepository.getAccount().collect { account ->
            if (account == null) throw AuthException()
            val wordTuple = wordsDao.getWordToLearn(account.id) ?: throw NoWordsToLearnException()
            val languageSetting = applicationSettings.getApplicationNativeLanguage()
            currentWordToLearn.emit(wordTuple.toWord(languageSetting))
        }
    }

    override suspend fun onWordKnown(word: Word) {
        accountsRepository.getAccount().collect { account ->
            if (account == null) throw AuthException()

            // lastRepeatedAt and startedAt should be the same
            // because that's the only way to differentiate KNOWN word from LEARNED
            val currentTimeMillis = System.currentTimeMillis()

            wordsDao.updateWordProgressAsKnown(
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

    override suspend fun onWordToLearn(word: Word) {
        accountsRepository.getAccount().collect { account ->
            if (account == null) throw AuthException()
            wordsDao.updateWordProgressAsLearning(
                UpdateWordProgressAsLearningTuple(
                    accountId = account.id,
                    wordId = word.id,
                    startedAt = System.currentTimeMillis()
                )
            )
            getWordToLearn()
        }
    }

    private fun queryWordsWithStatisticByWordSetId(wordSetId: Long, accountId: Long) : Flow<List<WordStatistic>>{
        return wordsDao.getWordsWithStatisticByWordSetId(wordSetId, accountId)
            .map { entities ->
                entities.map {
                    it.toWordStatistic()
                }
            }
    }
}