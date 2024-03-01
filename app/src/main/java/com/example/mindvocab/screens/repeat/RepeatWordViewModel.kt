package com.example.mindvocab.screens.repeat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.model.ErrorResult
import com.example.mindvocab.model.NoWordsToRepeatException
import com.example.mindvocab.model.Result
import com.example.mindvocab.model.SuccessResult
import com.example.mindvocab.model.repeating.RepeatingRepository
import com.example.mindvocab.model.word.entities.WordToRepeat
import kotlinx.coroutines.launch

class RepeatWordViewModel(
    private val repeatingRepository: RepeatingRepository
) : BaseViewModel() {

    private val _wordToRepeat = MutableLiveData<Result<WordToRepeat>>()
    val wordToRepeat: LiveData<Result<WordToRepeat>> = _wordToRepeat

    init {
        listenWordToRepeat()
        getWordToRepeat()
    }

    private fun listenWordToRepeat() {
        viewModelScope.launch {
            repeatingRepository.listenWordToRepeat().collect {
                _wordToRepeat.value = SuccessResult(it)
            }
        }
    }

    fun getWordToRepeat() {
        viewModelScope.launch {
            try {
                repeatingRepository.getWordToRepeat()
            } catch (e: NoWordsToRepeatException) {
                _wordToRepeat.value = ErrorResult(e)
            }
        }
    }

    fun onWordRemember(word: WordToRepeat) {
        viewModelScope.launch {
            try {
                repeatingRepository.onWordRemember(word)
            } catch (e: NoWordsToRepeatException) {
                _wordToRepeat.value = ErrorResult(e)
            }
        }
    }

    fun onWordForgot(word: WordToRepeat) {
        viewModelScope.launch {
            try {
                repeatingRepository.onWordForgot(word)
            } catch (e: NoWordsToRepeatException) {
                _wordToRepeat.value = ErrorResult(e)
            }
        }
    }
}