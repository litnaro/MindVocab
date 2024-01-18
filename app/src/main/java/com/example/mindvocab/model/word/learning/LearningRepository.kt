package com.example.mindvocab.model.word.learning

import com.example.mindvocab.model.word.learning.room.LearningListener

interface LearningRepository {

    fun onWordKnown()

    fun onWordToLearn()

    fun onWordListen()

    fun onSentenceListen(sentence: String)

    fun addListener(listener: LearningListener)

    fun removeListener(listener: LearningListener)

    fun notifyUpdates()
}