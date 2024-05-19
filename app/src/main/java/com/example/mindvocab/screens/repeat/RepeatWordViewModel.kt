package com.example.mindvocab.screens.repeat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.core.Result
import com.example.mindvocab.model.repeating.RepeatingRepository
import com.example.mindvocab.model.settings.repeat.RepeatSettings
import com.example.mindvocab.model.settings.repeat.options.AnsweringVariantSetting
import com.example.mindvocab.model.settings.repeat.options.QuestionVariantSetting
import com.example.mindvocab.model.word.entities.WordToRepeat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepeatWordViewModel @Inject constructor(
    private val repeatingRepository: RepeatingRepository,
    private val repeatSettings: RepeatSettings
) : BaseViewModel() {

    private val _wordToRepeatLiveDataResult = MutableLiveData<Result<WordToRepeat>>(Result.Pending)
    val wordToRepeatLiveDataResult: LiveData<Result<WordToRepeat>> = _wordToRepeatLiveDataResult

    private val _questionVariantSettingLiveData = MutableLiveData<QuestionVariantSetting>()
    val questionVariantSettingLiveData: LiveData<QuestionVariantSetting> = _questionVariantSettingLiveData

    private val _answeringVariantSettingLiveData = MutableLiveData<AnsweringVariantSetting>()
    val answeringVariantSettingLiveData: LiveData<AnsweringVariantSetting> = _answeringVariantSettingLiveData

    init {
        listenWordToRepeat()
        getWordToRepeat()
        listenQuestionVariantSetting()
        listenAnsweringVariantSetting()
    }

    fun getWordToRepeat() = _wordToRepeatLiveDataResult.trackActionExecutionNoResult {
        repeatingRepository.getWordToRepeat()
    }

    fun onWordRemember(word: WordToRepeat) = _wordToRepeatLiveDataResult.trackActionExecutionNoResult {
        repeatingRepository.onWordRemember(word)
    }

    fun onWordForgot(word: WordToRepeat) = _wordToRepeatLiveDataResult.trackActionExecutionNoResult {
        repeatingRepository.onWordForgot(word)
    }

    private fun listenWordToRepeat() = _wordToRepeatLiveDataResult.trackActionExecutionWithFlowResult {
        repeatingRepository.listenWordToRepeat()
    }

    private fun listenQuestionVariantSetting() {
        viewModelScope.launch {
            repeatSettings.questionVariantSetting.collect {
                _questionVariantSettingLiveData.value = it
            }
        }
    }

    private fun listenAnsweringVariantSetting() {
        viewModelScope.launch {
            repeatSettings.answeringVariantSetting.collect {
                _answeringVariantSettingLiveData.value = it
            }
        }
    }

}