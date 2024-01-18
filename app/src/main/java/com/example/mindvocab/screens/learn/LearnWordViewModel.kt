package com.example.mindvocab.screens.learn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mindvocab.model.Result
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.model.AppException
import com.example.mindvocab.model.ErrorResult
import com.example.mindvocab.model.PendingResult
import com.example.mindvocab.model.word.learning.LearningRepository
import com.example.mindvocab.model.word.learning.entities.WordToLearn
import com.example.mindvocab.model.word.learning.room.LearningListener

class LearnWordViewModel(
    private val learningRepository: LearningRepository
) : BaseViewModel() {

    private val _word = MutableLiveData<Result<WordToLearn>>(PendingResult())
    val word: LiveData<Result<WordToLearn>> = _word

    private val listener = object : LearningListener {
        override fun invoke(word: Result<WordToLearn>) {
            _word.value = word
        }
    }

    init {
        learningRepository.addListener(listener)
    }

    fun onWordKnown() {
        try {
            learningRepository.onWordKnown()
        } catch (e: AppException) {
            _word.value = ErrorResult(e)
        }
    }

    fun onWordLearn() {
        learningRepository.onWordToLearn()
    }

    fun onSentenceListen(sentence: String) {

    }

    fun onWordListen() {

    }
}