package com.example.mindvocab.screens.learn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.model.Result
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.model.AppException
import com.example.mindvocab.model.ErrorResult
import com.example.mindvocab.model.NoWordsToLearnException
import com.example.mindvocab.model.PendingResult
import com.example.mindvocab.model.SuccessResult
import com.example.mindvocab.model.learning.LearningRepository
import com.example.mindvocab.model.word.entities.Word
import kotlinx.coroutines.launch

class LearnWordViewModel(
    private val learningRepository: LearningRepository
) : BaseViewModel() {

    private val _word = MutableLiveData<Result<Word>>(PendingResult())
    val word: LiveData<Result<Word>> = _word

    init {
        listenWordToLearn()
        getWordToLearn()
    }

    private fun listenWordToLearn() {
        viewModelScope.launch {
            learningRepository.listenWordToLearn().collect {
                _word.value = SuccessResult(it)
            }
        }
    }

    fun getWordToLearn() {
        viewModelScope.launch {
            try {
                learningRepository.getWordToLearn()
            } catch (e: NoWordsToLearnException) {
                _word.value = ErrorResult(e)
            }
        }
    }

    fun onWordKnown(word: Word) {
        viewModelScope.launch {
            try {
                learningRepository.onWordKnown(word)
            } catch (e: AppException) {
                _word.value = ErrorResult(e)
            }
        }
    }

    fun onWordToLearn(word: Word) {
        viewModelScope.launch {
            try {
                learningRepository.onWordToLearn(word)
            } catch (e: AppException) {
                _word.value = ErrorResult(e)
            }
        }
    }

    fun onSentenceListen(sentence: String) {

    }

    fun onWordListen() {

    }
}