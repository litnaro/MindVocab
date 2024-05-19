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

    private val _wordSetFilterLiveData = MutableLiveData(WordSetFilter.ALL)
    val wordSetFilterLiveData: LiveData<WordSetFilter> = _wordSetFilterLiveData

    private val _wordSetListLiveData = MutableLiveData<Result<List<WordSet>>>(Result.Pending)
    val wordSetListLiveData: LiveData<Result<List<WordSet>>> = _wordSetListLiveData

    init {
        getWordSets()
    }

    fun getWordSets(searchQuery: String = "", filter: WordSetFilter = WordSetFilter.ALL) {
        viewModelScope.launch {
            if (searchQuery.isNotBlank()) _wordSetFilterLiveData.value = WordSetFilter.ALL
            wordSetsRepository.getWordSets(searchQuery, filter).collect {
                _wordSetListLiveData.value = Result.Success(it)
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