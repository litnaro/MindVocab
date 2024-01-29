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
import com.example.mindvocab.model.sets.room.entity.WordSetDbEntity
import kotlinx.coroutines.launch

class WordSetsViewModel(
    private val wordSetsRepository: WordSetsRepository
) : BaseViewModel() {


    private val _wordSetList = MutableLiveData<Result<List<WordSet>>>()
    val wordSetList: LiveData<Result<List<WordSet>>> = _wordSetList

    init {
        viewModelScope.launch {
            wordSetsRepository.getWordSets().collect {
                _wordSetList.value = SuccessResult(it)
            }
        }
    }

    fun getWordSetList(filter: WordSetFilter = WordSetFilter.ALL) {

    }

    fun selectWordSet(wordSet: WordSet) {

    }

    fun createWordSet() {
        viewModelScope.launch {
            wordSetsRepository.createWordSet(
                WordSetDbEntity(
                    id = 0,
                    name = "Okay",
                    image = "ByteArray(1)"
                )
            )
        }
    }
}