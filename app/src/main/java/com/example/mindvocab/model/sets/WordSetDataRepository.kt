package com.example.mindvocab.model.sets

import com.github.javafaker.Faker
import kotlin.random.Random

typealias WordSetListener = (wordSet: List<WordSet>) -> Unit

class WordSetDataRepository : WordSetRepository {

    private val random = Random(1)
    private val faker = Faker.instance()

    private val listeners = mutableSetOf<WordSetListener>()

    private val wordSets = MutableList(20) {
        WordSet(
            id = it + 1,
            name = faker.cat().name(),
            photo = "https://source.unsplash.com/random?cat&iddqd=${random.nextInt()}",
            wordsList = emptyList()
        )
    }

    override fun getPlatformWordSets(): List<WordSet> {
        return wordSets
    }

    override fun createWordSet(wordSet: WordSet): Boolean {
        TODO("Not yet implemented")
    }

    override fun selectWordSetToLearn(wordSet: WordSet): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeWordSetFromLearning(wordSet: WordSet): Boolean {
        TODO("Not yet implemented")
    }

    override fun addListener(listener: WordSetListener) {
        listeners.add(listener)
        listener.invoke(getPlatformWordSets())
    }

    override fun removeListener(listener: WordSetListener) {
        listeners.remove(listener)
    }

    override fun notifyUpdates() {
        listeners.forEach { it.invoke(getPlatformWordSets()) }
    }

}