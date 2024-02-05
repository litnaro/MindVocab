package com.example.mindvocab.screens.word

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.model.Result
import com.example.mindvocab.model.SuccessResult
import com.example.mindvocab.model.word.WordRepository
import com.example.mindvocab.model.word.entities.WordStatistic
import kotlinx.coroutines.launch

class WordsViewModel(
    private val wordRepository: WordRepository
) : BaseViewModel() {


    private val _wordsList = MutableLiveData<Result<List<WordStatistic>>>()
    val wordsList: LiveData<Result<List<WordStatistic>>> = _wordsList

    fun getWordsByWordSetId(wordSetId: Long) {
        viewModelScope.launch {
            wordRepository.getWordsByWordSetId(wordSetId).collect {
                _wordsList.value = SuccessResult(it)
            }
        }
    }

}