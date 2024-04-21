package com.example.mindvocab.screens.repeat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.model.AppException
import com.example.mindvocab.model.repeating.NoWordsToRepeatException
import com.example.mindvocab.core.Result
import com.example.mindvocab.model.repeating.RepeatingRepository
import com.example.mindvocab.model.settings.repeat.RepeatSettings
import com.example.mindvocab.model.settings.repeat.options.AnsweringVariantSetting
import com.example.mindvocab.model.settings.repeat.options.QuestionVariantSetting
import com.example.mindvocab.model.word.entities.WordToRepeat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepeatWordViewModel @Inject constructor(
    private val repeatingRepository: RepeatingRepository,
    private val repeatSettings: RepeatSettings
) : BaseViewModel() {

    private val _wordToRepeat = MutableLiveData<Result<WordToRepeat>>(Result.Pending)
    val wordToRepeat: LiveData<Result<WordToRepeat>> = _wordToRepeat

    private val _questionVariantSetting = MutableLiveData<QuestionVariantSetting>()
    val questionVariantSetting: LiveData<QuestionVariantSetting> = _questionVariantSetting

    private val _answeringVariantSetting = MutableLiveData<AnsweringVariantSetting>()
    val answeringVariantSetting: LiveData<AnsweringVariantSetting> = _answeringVariantSetting

    init {
        listenWordToRepeat()
        getWordToRepeat()
        listenQuestionVariantSetting()
        listenAnsweringVariantSetting()
    }

    fun getWordToRepeat() {
        viewModelScope.launch {
            delay(1500)
            try {
                repeatingRepository.getWordToRepeat()
            } catch (e: AppException) {
                _wordToRepeat.value = Result.Error(e)
            }
        }
    }

    fun onWordRemember(word: WordToRepeat) {
        viewModelScope.launch {
            try {
                repeatingRepository.onWordRemember(word)
            } catch (e: AppException) {
                _wordToRepeat.value = Result.Error(e)
            }
        }
    }

    fun onWordForgot(word: WordToRepeat) {
        viewModelScope.launch {
            try {
                repeatingRepository.onWordForgot(word)
            } catch (e: NoWordsToRepeatException) {
                _wordToRepeat.value = Result.Error(e)
            }
        }
    }

    private fun listenWordToRepeat() {
        viewModelScope.launch {
            repeatingRepository.listenWordToRepeat().collect {
                _wordToRepeat.value = Result.Success(it)
            }
        }
    }

    private fun listenQuestionVariantSetting() {
        viewModelScope.launch {
            repeatSettings.questionVariantSetting.collect {
                _questionVariantSetting.value = it
            }
        }
    }

    private fun listenAnsweringVariantSetting() {
        viewModelScope.launch {
            repeatSettings.answeringVariantSetting.collect {
                _answeringVariantSetting.value = it
            }
        }
    }

}