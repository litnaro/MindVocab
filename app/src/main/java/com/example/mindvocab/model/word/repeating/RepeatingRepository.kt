package com.example.mindvocab.model.word.repeating

import com.example.mindvocab.model.Repository
import com.example.mindvocab.model.word.learning.entities.WordToLearn
import com.example.mindvocab.model.word.learning.room.LearningListener

interface RepeatingRepository : Repository {

    fun onWordRemember(word: WordToLearn) : Boolean

    fun onWordNotRemember(word: WordToLearn) : Boolean

    fun addListener(listener: LearningListener)

    fun removeListener(listener: LearningListener)
}