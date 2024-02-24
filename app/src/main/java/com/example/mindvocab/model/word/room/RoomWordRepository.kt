package com.example.mindvocab.model.word.room

import com.example.mindvocab.model.account.AccountsRepository
import com.example.mindvocab.model.word.WordRepository
import com.example.mindvocab.model.word.entities.WordStatistic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class RoomWordRepository(
    private val wordsDao: WordsDao,
    private val accountsRepository: AccountsRepository,
) : WordRepository {

    override suspend fun getWordsByWordSetId(wordSetId: Long): Flow<List<WordStatistic>> {
        return accountsRepository.getAccount()
            .flatMapLatest { account ->
                if (account == null) return@flatMapLatest flowOf(emptyList())
                queryWordsWithStatisticByWordSetId(wordSetId ,account.id)
            }
    }

    private fun queryWordsWithStatisticByWordSetId(wordSetId: Long, accountId: Long) : Flow<List<WordStatistic>> {
        return wordsDao.getWordsWithStatisticByWordSetId(wordSetId, accountId)
            .map { entities ->
                entities.map {
                    it.toWordStatistic()
                }
            }
    }
}