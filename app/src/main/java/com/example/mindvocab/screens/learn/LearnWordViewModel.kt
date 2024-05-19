package com.example.mindvocab.screens.learn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.Result
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.model.learning.LearningRepository
import com.example.mindvocab.model.settings.learn.LearningSettings
import com.example.mindvocab.model.settings.learn.options.WordsADaySetting
import com.example.mindvocab.model.word.entities.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LearnWordViewModel @Inject constructor(
    private val learningRepository: LearningRepository,
    private val learningSettings: LearningSettings
) : BaseViewModel() {

    private val _wordLiveDataResult = MutableLiveData<Result<Word>>(Result.Pending)
    val wordLiveDataResult: LiveData<Result<Word>> = _wordLiveDataResult

    private val _maxWordsForTodayLiveData = MutableLiveData<WordsADaySetting>()
    val maxWordsForTodayLiveData: LiveData<WordsADaySetting> = _maxWordsForTodayLiveData

    private val _startedTodayWordsCountLiveData = MutableLiveData<Int>()
    val startedTodayWordsCountLiveData: LiveData<Int> = _startedTodayWordsCountLiveData

    private val _isPreviousWordAvailableLiveData = MutableLiveData<Boolean>()
    val isPreviousWordAvailableLiveData: LiveData<Boolean> = _isPreviousWordAvailableLiveData

    init {
        listenIsReturnPreviousWordEnabled()
        listenWordToLearn()
        listenWordADaySettings()
        listenTodayStartedWords()
        getWordToLearn()
    }

    fun getWordToLearn() = _wordLiveDataResult.trackActionExecutionNoResult {
        learningRepository.getWordToLearn()
    }

    fun onWordKnown(word: Word) = _wordLiveDataResult.trackActionExecutionNoResult {
        learningRepository.onWordKnown(word)
    }

    fun onWordToLearn(word: Word) = _wordLiveDataResult.trackActionExecutionNoResult {
        learningRepository.onWordToLearn(word)
    }

    fun onWordReturnPrevious() {
        viewModelScope.launch {
            learningRepository.returnPreviousWord()
        }
    }

    private fun listenWordToLearn() = _wordLiveDataResult.trackActionExecutionWithFlowResult {
        learningRepository.listenWordToLearn()
    }

    private fun listenWordADaySettings() {
        viewModelScope.launch {
            learningSettings.wordsADaySetting.collect {
                _maxWordsForTodayLiveData.value = it
            }
        }
    }

    private fun listenTodayStartedWords() {
        viewModelScope.launch {
            learningRepository.getTodayStartedWordsCount().collect {
                _startedTodayWordsCountLiveData.value = it
            }
        }
    }

    private fun listenIsReturnPreviousWordEnabled() {
        viewModelScope.launch {
            learningRepository.listenIsReturnPreviousWordEnabled().collect {
                _isPreviousWordAvailableLiveData.value = it
            }
        }
    }
}