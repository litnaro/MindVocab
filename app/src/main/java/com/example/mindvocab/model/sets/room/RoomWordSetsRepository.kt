package com.example.mindvocab.model.sets.room

import com.example.mindvocab.model.AuthException
import com.example.mindvocab.model.StorageException
import com.example.mindvocab.model.account.AccountsRepository
import com.example.mindvocab.model.sets.WordSetFilter
import com.example.mindvocab.model.sets.WordSetsRepository
import com.example.mindvocab.model.sets.entity.WordSet
import com.example.mindvocab.model.sets.room.entity.AccountWordSetDbEntity
import com.example.mindvocab.model.word.WordsCalculations
import com.example.mindvocab.model.word.room.WordsDao
import com.example.mindvocab.model.word.room.entities.AccountWordProgressDbEntity
import com.example.mindvocab.model.word.room.entities.WordDbEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomWordSetsRepository @Inject constructor(
    private val wordSetsDao: WordSetsDao,
    private val wordsDao: WordsDao,
    private val accountsRepository: AccountsRepository,
    private val ioDispatcher: CoroutineDispatcher
) : WordSetsRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getWordSets(searchQuery: String, filter: WordSetFilter): Flow<List<WordSet>> {
        val account = accountsRepository.getAccount().first() ?: throw AuthException()
        if (searchQuery.isBlank()) {
            return queryWordSets(account.id).mapLatest { wordSets ->
                when(filter) {
                    WordSetFilter.ALL -> {
                        wordSets
                    }
                    WordSetFilter.SELECTED -> {
                        wordSets.filter { it.isSelected }
                    }
                    WordSetFilter.UNSELECTED -> {
                        wordSets.filter { !it.isSelected }
                    }
                }
            }
        } else {
            return queryWordSets(account.id, searchQuery)
        }
    }

    override suspend fun selectWordSet(wordSet: WordSet) = withContext(ioDispatcher) {
        setSelectedFlagForWordSet(wordSet, true)
    }

    override suspend fun unselectWordSet(wordSet: WordSet) = withContext(ioDispatcher) {
        setSelectedFlagForWordSet(wordSet, false)
    }

    private fun queryWordSets(accountId: Long): Flow<List<WordSet>> {
        return wordSetsDao.getWordSetWithStatistic(accountId, WordsCalculations.TIMES_REPEATED_TO_LEARN).map { it ->
            it.map {
                it.toWordSet()
            }
        }
    }

    private fun queryWordSets(accountId: Long, searchQuery: String): Flow<List<WordSet>> {
        return wordSetsDao.getWordSetWithStatistic(accountId, WordsCalculations.TIMES_REPEATED_TO_LEARN, "%$searchQuery%").map { it ->
            it.map {
                it.toWordSet()
            }
        }
    }

    private suspend fun setSelectedFlagForWordSet(wordSet: WordSet, isSelected: Boolean) = withContext(ioDispatcher) {
        val account = accountsRepository.getAccount().first() ?: throw AuthException()
        val wordSetDbEntity = AccountWordSetDbEntity(
            accountId = account.id,
            wordSetId = wordSet.id,
            isSelected = isSelected
        )

        if (isSelected) {
            val accountWordsProgress: List<WordDbEntity>?
            try {
                accountWordsProgress = wordsDao.getWordsByWordSetId(wordSet.id).first()
            } catch (e: Exception) {
               throw StorageException()
            }
            wordSetsDao.upsertWordSetSelection(
                wordSetDbEntity,
                accountWordsProgress.map {
                    AccountWordProgressDbEntity(
                        accountId = account.id,
                        wordId = it.id,
                        timesRepeated = 0,
                        lastRepeatedAt = 0,
                        startedAt = 0
                    )
                }
            )
        } else {
            wordSetsDao.upsertWordSetSelection(wordSetDbEntity)
        }
    }

}