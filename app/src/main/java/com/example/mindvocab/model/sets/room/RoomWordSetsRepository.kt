package com.example.mindvocab.model.sets.room

import com.example.mindvocab.model.StorageException
import com.example.mindvocab.model.sets.WordSetFilter
import com.example.mindvocab.model.sets.WordSetsRepository
import com.example.mindvocab.model.sets.entity.WordSet
import com.example.mindvocab.model.sets.room.entity.WordSetDbEntity
import com.github.javafaker.Faker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.random.Random

typealias WordSetListener = (wordSet: List<WordSet>) -> Unit

class RoomWordSetsRepository(
    private val wordSetsDao: WordSetsDao
) : WordSetsRepository {

    override suspend fun getWordSets(filter: WordSetFilter): Flow<List<WordSet>> {
        return wordSetsDao.getWordSets().map { it ->
            it.map {
                it.toWordSet()
            }
        }
    }

    override suspend fun createWordSet(wordSet: WordSetDbEntity): Boolean {
        try {
            wordSetsDao.createWordSet(wordSet)
        } catch (e: StorageException) {
            e.printStackTrace()
        }
        return true
    }

    override suspend fun deleteWordSet(wordSet: WordSetDbEntity): Boolean {
        TODO("Not yet implemented")
    }

    override fun selectWordSet(wordSet: WordSet): Boolean {
        TODO("Not yet implemented")
    }

    override fun unselectWordSet(wordSet: WordSet): Boolean {
        TODO("Not yet implemented")
    }

}