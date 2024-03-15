package com.example.mindvocab.screens.learn.wordset.words

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.core.Result
import com.example.mindvocab.model.word.WordsRepository
import com.example.mindvocab.model.word.entities.WordStatistic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordsViewModel @Inject constructor(
    private val wordsRepository: WordsRepository
) : BaseViewModel() {


    private val _wordsList = MutableLiveData<Result<List<WordStatistic>>>(Result.PendingResult())
    val wordsList: LiveData<Result<List<WordStatistic>>> = _wordsList

    fun getWordsByWordSetId(wordSetId: Long) {
        viewModelScope.launch {
            delay(1500)
            wordsRepository.getWordsByWordSetId(wordSetId).collect {
                _wordsList.value = Result.SuccessResult(it)
            }
        }
    }

}