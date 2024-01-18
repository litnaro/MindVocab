package com.example.mindvocab.model.word.learning.room

import com.example.mindvocab.model.Result
import com.example.mindvocab.model.SuccessResult
import com.example.mindvocab.model.WordsEndedException
import com.example.mindvocab.model.word.learning.entities.WordToLearn
import com.example.mindvocab.model.word.learning.LearningRepository
import com.github.javafaker.Faker
import kotlin.random.Random

typealias LearningListener = (word: Result<WordToLearn>) -> Unit

class RoomLearningRepository : LearningRepository {

    private val random = Random(1)
    private val faker = Faker.instance()

    private val listeners = mutableSetOf<LearningListener>()

    private var currentWord: WordToLearn? = null

    private val wordSet = MutableList(3) {
        val id = (it + 1).toLong()
        WordToLearn(
            id = id,
            word = faker.cat().name(),
            audio = "",
            image = "https://source.unsplash.com/random?cat&iddqd=${random.nextInt()}",
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
        )
    }

    private fun getWordForLearning(): WordToLearn {
        if (wordSet.isEmpty()) {
            throw WordsEndedException("No elements")
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

    override fun onWordListen() {
        TODO("Not yet implemented")
    }

    override fun onSentenceListen(sentence: String) {
        TODO("Not yet implemented")
    }

    override fun addListener(listener: LearningListener) {
        listeners.add(listener)

        currentWord = getWordForLearning().also {
            listener.invoke(SuccessResult(it))
        }
    }

    override fun removeListener(listener: LearningListener) {
        listeners.remove(listener)
    }

    override fun notifyUpdates() {
        listeners.forEach { it.invoke(SuccessResult(currentWord!!)) }
    }

}