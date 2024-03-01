package com.example.mindvocab.model.sets.room

import com.example.mindvocab.model.AuthException
import com.example.mindvocab.model.StorageException
import com.example.mindvocab.model.account.AccountsRepository
import com.example.mindvocab.model.sets.WordSetFilter
import com.example.mindvocab.model.sets.WordSetsRepository
import com.example.mindvocab.model.sets.entity.WordSet
import com.example.mindvocab.model.sets.room.entity.AccountWordSetDbEntity
import com.example.mindvocab.model.sets.room.entity.WordSetDbEntity
import com.example.mindvocab.model.word.room.WordsDao
import com.example.mindvocab.model.word.room.entities.AccountWordProgressDbEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext

class RoomWordSetsRepository(
    private val wordSetsDao: WordSetsDao,
    private val wordsDao: WordsDao,
    private val accountsRepository: AccountsRepository,
    private val ioDispatcher: CoroutineDispatcher
) : WordSetsRepository {

    override suspend fun getWordSets(filter: WordSetFilter): Flow<List<WordSet>> {
        return accountsRepository.getAccount()
            .flatMapLatest { account ->
                if (account == null) return@flatMapLatest flowOf(emptyList())
                queryWordSets(account.id)
            }
            .mapLatest { wordSets ->
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
    }

    override suspend fun createWordSet(wordSet: WordSetDbEntity) = withContext(ioDispatcher) {
        try {
            wordSetsDao.createWordSet(wordSet)
        } catch (e: StorageException) {
            e.printStackTrace()
        }
    }

    override suspend fun deleteWordSet(wordSet: WordSetDbEntity) = withContext(ioDispatcher) {
        TODO("Not yet implemented")
    }

    override suspend fun selectWordSet(wordSet: WordSet) = withContext(ioDispatcher) {
        setSelectedFlagForWordSet(wordSet, true)
    }

    override suspend fun unselectWordSet(wordSet: WordSet) = withContext(ioDispatcher) {
        setSelectedFlagForWordSet(wordSet, false)
    }

    private fun queryWordSets(accountId: Long): Flow<List<WordSet>>{
        return wordSetsDao.getWordSets(accountId).map { it ->
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
            val accountWordsProgress = wordsDao.getWordsByWordSetId(wordSet.id).first().map {
                AccountWordProgressDbEntity(
                    accountId = account.id,
                    wordId = it.id,
                    timesRepeated = 0,
                    lastRepeatedAt = 0,
                    startedAt = 0
                )
            }
            wordSetsDao.upsertWordSetSelection(
                wordSetDbEntity,
                accountWordsProgress
            )
        } else {
            wordSetsDao.upsertWordSetSelection(wordSetDbEntity)
        }
    }

}