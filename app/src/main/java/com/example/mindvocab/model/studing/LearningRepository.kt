package com.example.mindvocab.model.studing

import com.example.mindvocab.model.word.Word
import com.github.javafaker.Faker
import kotlin.random.Random

typealias LearningListener = (word: Word) -> Unit

class LearningRepository : LearningSource {

    private val random = Random(1)
    private val faker = Faker.instance()

    private val listeners = mutableSetOf<LearningListener>()

    private var currentWord: Word? = null

    private val wordSet = MutableList(10) {
        val id = it + 1
        Word(
            id = id,
            word = faker.cat().name(),
            audio = "",
            photo = "https://source.unsplash.com/random?cat&iddqd=${random.nextInt()}",
            transcription = "[${faker.cat().name()}]",
            explanation = "${faker.cat().name()}; ${faker.cat().name()}",
            translationList = listOf(faker.cat().name(), faker.cat().name(), faker.cat().name()),
            exampleList = listOf(
                faker.lorem().sentence(5, 3),
                faker.lorem().sentence(5, 3),
                faker.lorem().sentence(5, 3),
                faker.lorem().sentence(5, 3),
                faker.lorem().sentence(5, 3)
            ),
            progress = 0,
            lastRepeated = 0
        )
    }

    override fun getWordForLearning(): Word {
        if (wordSet.isEmpty()) {
            throw IllegalArgumentException("No elements")
        }
        return wordSet.removeAt(Random.nextInt(wordSet.size))
    }

    override fun onWordKnown() {
        currentWord = getWordForLearning()
        notifyUpdates()
    }

    override fun onWordToLearn() {
        currentWord = getWordForLearning()
        notifyUpdates()
    }

    override fun addListener(listener: LearningListener) {
        listeners.add(listener)
        if (currentWord == null) {
            currentWord = getWordForLearning().also { listener.invoke(it) }
        }
    }

    override fun removeListener(listener: LearningListener) {
        listeners.remove(listener)
    }

    override fun notifyUpdates() {
        listeners.forEach { it.invoke(currentWord!!) }
    }

}