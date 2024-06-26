package com.example.mindvocab.model.word.room

import com.example.mindvocab.model.AuthException
import com.example.mindvocab.model.account.AccountsRepository
import com.example.mindvocab.model.settings.application.ApplicationSettings
import com.example.mindvocab.model.word.WordsRepository
import com.example.mindvocab.model.word.entities.WordStatistic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomWordsRepository @Inject constructor(
    private val wordsDao: WordsDao,
    private val accountsRepository: AccountsRepository,
    private val applicationSettings: ApplicationSettings,
) : WordsRepository {

    override fun getWordsByWordSetId(wordSetId: Long): Flow<List<WordStatistic>> {
        return accountsRepository.getAccount()
            .flatMapLatest { account ->
                if (account == null) throw AuthException()
                queryWordsWithStatisticByWordSetId(wordSetId, account.id)
            }
    }

    private suspend fun queryWordsWithStatisticByWordSetId(wordSetId: Long, accountId: Long) : Flow<List<WordStatistic>> {
        val languageSetting = applicationSettings.nativeLanguage.first()
        return wordsDao.getWordsWithStatisticByWordSetId(accountId, wordSetId, languageSetting.value)
            .map { entities ->
                entities.map {
                    it.toWordStatistic()
                }
            }
    }
}