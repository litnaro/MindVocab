package com.example.mindvocab.screens.repeat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.model.AppException
import com.example.mindvocab.model.NoWordsToRepeatException
import com.example.mindvocab.core.Result
import com.example.mindvocab.model.repeating.RepeatingRepository
import com.example.mindvocab.model.word.entities.WordToRepeat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepeatWordViewModel @Inject constructor(
    private val repeatingRepository: RepeatingRepository
) : BaseViewModel() {

    private val _wordToRepeat = MutableLiveData<Result<WordToRepeat>>(Result.PendingResult())
    val wordToRepeat: LiveData<Result<WordToRepeat>> = _wordToRepeat

    init {
        listenWordToRepeat()
        getWordToRepeat()
    }

    private fun listenWordToRepeat() {
        viewModelScope.launch {
            repeatingRepository.listenWordToRepeat().collect {
                _wordToRepeat.value = Result.SuccessResult(it)
            }
        }
    }

    fun getWordToRepeat() {
        viewModelScope.launch {
            delay(1500)
            try {
                repeatingRepository.getWordToRepeat()
            } catch (e: AppException) {
                _wordToRepeat.value = Result.ErrorResult(e)
            }
        }
    }

    fun onWordRemember(word: WordToRepeat) {
        viewModelScope.launch {
            try {
                repeatingRepository.onWordRemember(word)
            } catch (e: AppException) {
                _wordToRepeat.value = Result.ErrorResult(e)
            }
        }
    }

    fun onWordForgot(word: WordToRepeat) {
        viewModelScope.launch {
            try {
                repeatingRepository.onWordForgot(word)
            } catch (e: NoWordsToRepeatException) {
                _wordToRepeat.value = Result.ErrorResult(e)
            }
        }
    }
}