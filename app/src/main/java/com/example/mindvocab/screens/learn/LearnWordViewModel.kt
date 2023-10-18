package com.example.mindvocab.screens.learn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.model.studing.LearningListener
import com.example.mindvocab.model.studing.LearningSource
import com.example.mindvocab.model.word.Word

class LearnWordViewModel(
    private val learningSource: LearningSource
) : BaseViewModel() {

    private val _word = MutableLiveData<Word>()
    val word: LiveData<Word> = _word

    private val listener = object : LearningListener {
        override fun invoke(word: Word) {
            _word.value = word
        }
    }

    init {
        learningSource.addListener(listener)
    }

    fun onWordKnown() {
        learningSource.onWordKnown()
    }

    fun onWordLearn() {
        learningSource.onWordToLearn()
    }
}