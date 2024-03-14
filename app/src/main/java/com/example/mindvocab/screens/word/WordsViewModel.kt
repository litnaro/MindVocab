package com.example.mindvocab.screens.word

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.model.Result
import com.example.mindvocab.model.SuccessResult
import com.example.mindvocab.model.word.WordsRepository
import com.example.mindvocab.model.word.entities.WordStatistic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordsViewModel @Inject constructor(
    private val wordsRepository: WordsRepository
) : BaseViewModel() {


    private val _wordsList = MutableLiveData<Result<List<WordStatistic>>>()
    val wordsList: LiveData<Result<List<WordStatistic>>> = _wordsList

    fun getWordsByWordSetId(wordSetId: Long) {
        viewModelScope.launch {
            wordsRepository.getWordsByWordSetId(wordSetId).collect {
                _wordsList.value = SuccessResult(it)
            }
        }
    }

}