package com.example.mindvocab.model.sets

import com.example.mindvocab.model.Repository

interface WordSetSource : Repository {

    fun getPlatformWordSets() : List<WordSet>

    fun createWordSet(wordSet: WordSet) : Boolean

    fun selectWordSetToLearn(wordSet: WordSet) : Boolean

    fun removeWordSetFromLearning(wordSet: WordSet) : Boolean

    fun addListener(listener: WordSetListener)

    fun removeListener(listener: WordSetListener)

    fun notifyUpdates()

}