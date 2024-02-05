package com.example.mindvocab.screens.learn.wordset

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.model.sets.entity.WordSet
import com.example.mindvocab.model.sets.WordSetFilter
import com.example.mindvocab.model.Result
import com.example.mindvocab.model.SuccessResult
import com.example.mindvocab.model.sets.WordSetsRepository
import kotlinx.coroutines.launch

class WordSetsViewModel(
    private val wordSetsRepository: WordSetsRepository
) : BaseViewModel() {

    private val _wordSetList = MutableLiveData<Result<List<WordSet>>>()
    val wordSetList: LiveData<Result<List<WordSet>>> = _wordSetList

    init {
        getWordSets()
    }

    fun getWordSets(filter: WordSetFilter = WordSetFilter.ALL) {
        viewModelScope.launch {
            wordSetsRepository.getWordSets().collect {
                _wordSetList.value = SuccessResult(it)
            }
        }
    }

    fun selectWordSet(wordSet: WordSet) {
        viewModelScope.launch {
            wordSetsRepository.selectWordSet(wordSet)
        }
    }

    fun unselectWordSet(wordSet: WordSet) {
        viewModelScope.launch {
            wordSetsRepository.unselectWordSet(wordSet)
        }
    }

}