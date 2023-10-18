package com.example.mindvocab.model.studing

import com.example.mindvocab.model.word.Word

interface LearningSource {

    fun getWordForLearning() : Word

    fun onWordKnown()

    fun onWordToLearn()

    fun addListener(listener: LearningListener)

    fun removeListener(listener: LearningListener)

    fun notifyUpdates()
}