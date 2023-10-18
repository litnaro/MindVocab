package com.example.mindvocab.model.studing

import com.example.mindvocab.model.word.Word

typealias RepeatingListener = (word: Word) -> Unit

class RepeatingRepository : RepeatingSource {

    override fun getWordForRepeat(): Word {
        TODO("Not yet implemented")
    }

    override fun onWordRemember(word: Word): Boolean {
        TODO("Not yet implemented")
    }

    override fun onWordNotRemember(word: Word): Boolean {
        TODO("Not yet implemented")
    }

    override fun addListener(listener: LearningListener) {
        TODO("Not yet implemented")
    }

    override fun removeListener(listener: LearningListener) {
        TODO("Not yet implemented")
    }
}