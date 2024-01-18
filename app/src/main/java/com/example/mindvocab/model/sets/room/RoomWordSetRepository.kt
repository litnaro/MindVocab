package com.example.mindvocab.model.sets.room

import com.example.mindvocab.model.sets.WordSetRepository
import com.example.mindvocab.model.sets.entity.WordSet
import com.example.mindvocab.model.sets.room.entity.WordSetDbEntity
import com.github.javafaker.Faker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.random.Random

typealias WordSetListener = (wordSet: List<WordSet>) -> Unit

class RoomWordSetRepository(
    private val wordSetsDao: WordSetsDao
) : WordSetRepository {

    private val random = Random(1)
    private val faker = Faker.instance()

    private val listeners = mutableSetOf<WordSetListener>()

    private val wordSets = MutableList(20) {
        WordSet(
            id = (it + 1).toLong(),
            name = faker.cat().name(),
            photo = "https://source.unsplash.com/random?cat&iddqd=${random.nextInt()}",
            isSelected = false,
            wordsCount = Random.nextInt(100),
            accountCompletedWordsCount = Random.nextInt(100)
        )
    }

    override suspend fun getWordSets(): Flow<List<WordSet>> {
        return wordSetsDao.getWordSets().map { it ->
            it.map {
                it.toWordSet()
            }
        }
    }

    override suspend fun createWordSet(wordSet: WordSetDbEntity): Boolean {
        wordSetsDao.createWordSet(wordSet)
        return true
    }

    override fun selectWordSetToLearn(wordSet: WordSet): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeWordSetFromLearning(wordSet: WordSet): Boolean {
        TODO("Not yet implemented")
    }

}