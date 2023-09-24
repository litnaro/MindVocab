package com.example.mindvocab.model.word

import com.example.mindvocab.model.Repository

interface WordRepository : Repository {

    fun getWordsToLearn() : List<Word>

    fun onWordKnown(word: Word)

    fun onWordNew(word: Word)


    fun getWordsToRepeat() : List<Word>

    fun onWordRemember(word: Word)

    fun onWordNotRemember(word: Word)


    fun onCorrectTheWord(word: Word)


    fun addListener(listener: WordListener)

    fun removeListener(listener: WordListener)

    fun notifyUpdates()

}