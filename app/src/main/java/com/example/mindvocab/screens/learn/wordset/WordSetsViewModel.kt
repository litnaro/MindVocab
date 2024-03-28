package com.example.mindvocab.screens.learn.wordset

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.model.sets.entity.WordSet
import com.example.mindvocab.model.sets.WordSetFilter
import com.example.mindvocab.core.Result
import com.example.mindvocab.model.sets.WordSetsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordSetsViewModel @Inject constructor(
    private val wordSetsRepository: WordSetsRepository
) : BaseViewModel() {

    private val _wordSetFilter = MutableLiveData(WordSetFilter.ALL)
    val wordSetFilter: LiveData<WordSetFilter> = _wordSetFilter

    private val _wordSetList = MutableLiveData<Result<List<WordSet>>>(Result.PendingResult())
    val wordSetList: LiveData<Result<List<WordSet>>> = _wordSetList

    init {
        getWordSets()
    }

    fun getWordSets(searchQuery: String = "", filter: WordSetFilter = WordSetFilter.ALL) {
        viewModelScope.launch {
            if (searchQuery.isNotBlank()) _wordSetFilter.value = WordSetFilter.ALL
            wordSetsRepository.getWordSets(searchQuery, filter).collect {
                _wordSetList.value = Result.SuccessResult(it)
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