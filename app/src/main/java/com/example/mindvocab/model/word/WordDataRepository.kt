package com.example.mindvocab.model.word

typealias WordListener = (words: List<Word>) -> Unit

class WordDataRepository : WordRepository {
    override fun getWordsToLearn(): List<Word> {
        TODO("Not yet implemented")
    }

    override fun onWordKnown(word: Word) {
        TODO("Not yet implemented")
    }

    override fun onWordNew(word: Word) {
        TODO("Not yet implemented")
    }

    override fun getWordsToRepeat(): List<Word> {
        TODO("Not yet implemented")
    }

    override fun onWordRemember(word: Word) {
        TODO("Not yet implemented")
    }

    override fun onWordNotRemember(word: Word) {
        TODO("Not yet implemented")
    }

    override fun onCorrectTheWord(word: Word) {
        TODO("Not yet implemented")
    }

    override fun addListener(listener: WordListener) {
        TODO("Not yet implemented")
    }

    override fun removeListener(listener: WordListener) {
        TODO("Not yet implemented")
    }

    override fun notifyUpdates() {
        TODO("Not yet implemented")
    }

}