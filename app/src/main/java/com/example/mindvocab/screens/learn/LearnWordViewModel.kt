package com.example.mindvocab.screens.learn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.Result
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.model.AppException
import com.example.mindvocab.model.learning.LearningRepository
import com.example.mindvocab.model.settings.learn.LearningSettings
import com.example.mindvocab.model.word.entities.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LearnWordViewModel @Inject constructor(
    private val learningRepository: LearningRepository,
    private val learningSettings: LearningSettings
) : BaseViewModel() {

    private val _word = MutableLiveData<Result<Word>>(Result.PendingResult())
    val word: LiveData<Result<Word>> = _word

    private val _maxWordsForToday = MutableLiveData<LearningSettings.WordsADaySetting>()
    val maxWordsForToday: LiveData<LearningSettings.WordsADaySetting> = _maxWordsForToday

    private val _startedTodayWordsCount = MutableLiveData<Int>()
    val startedTodayWordsCount: LiveData<Int> = _startedTodayWordsCount

    init {
        listenWordToLearn()
        getWordToLearn()
        listenWordADaySettings()
        listenTodayStartedWords()
    }

    fun getWordToLearn() {
        viewModelScope.launch {
            delay(1500)
            try {
                learningRepository.getWordToLearn()
            } catch (e: AppException) {
                _word.value = Result.ErrorResult(e)
            }
        }
    }

    fun onWordKnown(word: Word) {
        viewModelScope.launch {
            try {
                learningRepository.onWordKnown(word)
            } catch (e: AppException) {
                _word.value = Result.ErrorResult(e)
            }
        }
    }

    fun onWordToLearn(word: Word) {
        viewModelScope.launch {
            try {
                learningRepository.onWordToLearn(word)
            } catch (e: AppException) {
                _word.value = Result.ErrorResult(e)
            }
        }
    }

    fun onSentenceListen(sentence: String) {

    }

    fun onWordListen() {

    }

    private fun listenWordToLearn() {
        viewModelScope.launch {
            learningRepository.listenWordToLearn().collect {
                _word.value = Result.SuccessResult(it)
            }
        }
    }

    private fun listenWordADaySettings() {
        viewModelScope.launch {
            learningSettings.wordsADaySetting.collect {
                _maxWordsForToday.value = it
            }
        }
    }

    private fun listenTodayStartedWords() {
        viewModelScope.launch {
            learningRepository.getTodayStartedWordsCount().collect {
                _startedTodayWordsCount.value = it
            }
        }
    }
}