package com.example.mindvocab.screens.repeat.words

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.model.repeating.RepeatingRepository
import com.example.mindvocab.core.Result
import com.example.mindvocab.model.word.entities.WordToRepeatDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepeatingWordsViewModel @Inject constructor(
    private val repeatingRepository: RepeatingRepository
) : BaseViewModel() {

    private val _repeatingWords = MutableLiveData<Result<List<WordToRepeatDetail>>>(Result.PendingResult())
    val repeatingWords: LiveData<Result<List<WordToRepeatDetail>>> = _repeatingWords

    init {
        getRepeatingWords()
    }

    private fun getRepeatingWords() {
        viewModelScope.launch {
            delay(1500)
            repeatingRepository.getWordsToRepeat().collect {
                _repeatingWords.value = Result.SuccessResult(it)
            }
        }
    }

}