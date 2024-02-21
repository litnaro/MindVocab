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
import com.example.mindvocab.model.word.WordRepository
import com.example.mindvocab.model.word.entities.Word
import kotlinx.coroutines.launch

class LearnWordViewModel(
    private val wordRepository: WordRepository
) : BaseViewModel() {

    private val _word = MutableLiveData<Result<Word>>(PendingResult())
    val word: LiveData<Result<Word>> = _word

    init {
        listenWordToLearn()
        getWordToLearn()
    }

    private fun listenWordToLearn() {
        viewModelScope.launch {
            wordRepository.listenWordToLearn().collect {
                _word.value = SuccessResult(it)
            }
        }
    }

    private fun getWordToLearn() {
        viewModelScope.launch {
            try {
                wordRepository.getWordToLearn()
            } catch (e: NoWordsToLearnException) {
                _word.value = ErrorResult(e)
            }
        }
    }

    fun onWordKnown(word: Word) {
        viewModelScope.launch {
            try {
                wordRepository.onWordKnown(word)
            } catch (e: AppException) {
                _word.value = ErrorResult(e)
            }
        }
    }

    fun onWordToLearn(word: Word) {
        viewModelScope.launch {
            try {
                wordRepository.onWordToLearn(word)
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