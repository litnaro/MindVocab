package com.example.mindvocab.model.studing

import com.example.mindvocab.model.Repository
import com.example.mindvocab.model.word.Word

interface RepeatingSource : Repository {
    fun getWordForRepeat() : Word

    fun onWordRemember(word: Word) : Boolean

    fun onWordNotRemember(word: Word) : Boolean

    fun addListener(listener: LearningListener)

    fun removeListener(listener: LearningListener)
}