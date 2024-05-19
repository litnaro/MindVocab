package com.example.mindvocab.screens.repeat.words

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.model.repeating.RepeatingRepository
import com.example.mindvocab.core.Result
import com.example.mindvocab.model.word.entities.WordToRepeatDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RepeatingWordsViewModel @Inject constructor(
    private val repeatingRepository: RepeatingRepository
) : BaseViewModel() {

    private val _repeatingWordsLiveDataResult = MutableLiveData<Result<List<WordToRepeatDetail>>>(Result.Pending)
    val repeatingWordsLiveDataResult: LiveData<Result<List<WordToRepeatDetail>>> = _repeatingWordsLiveDataResult

    init {
        getRepeatingWords()
    }

    private fun getRepeatingWords() = _repeatingWordsLiveDataResult.trackActionExecutionWithFlowResult {
        repeatingRepository.getWordsToRepeat()
    }

}