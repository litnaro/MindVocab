package com.example.mindvocab.model.word.repeating.room

import com.example.mindvocab.model.word.learning.entities.WordToLearn
import com.example.mindvocab.model.word.learning.room.LearningListener
import com.example.mindvocab.model.word.repeating.RepeatingRepository

typealias RepeatingListener = (word: WordToLearn) -> Unit

class RoomRepeatingRepository : RepeatingRepository {

    private fun getWordForRepeat(): WordToLearn {
        TODO("Not yet implemented")
    }

    override fun onWordRemember(word: WordToLearn): Boolean {
        TODO("Not yet implemented")
    }

    override fun onWordNotRemember(word: WordToLearn): Boolean {
        TODO("Not yet implemented")
    }

    override fun addListener(listener: LearningListener) {
        TODO("Not yet implemented")
    }

    override fun removeListener(listener: LearningListener) {
        TODO("Not yet implemented")
    }
}