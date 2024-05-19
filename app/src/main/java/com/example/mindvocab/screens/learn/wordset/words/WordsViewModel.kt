package com.example.mindvocab.screens.learn.wordset.words

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.core.Result
import com.example.mindvocab.model.word.WordsRepository
import com.example.mindvocab.model.word.entities.WordStatistic
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WordsViewModel @Inject constructor(
    private val wordsRepository: WordsRepository
) : BaseViewModel() {

    private val _wordsListLiveData = MutableLiveData<Result<List<WordStatistic>>>(Result.Pending)
    val wordsListLiveData: LiveData<Result<List<WordStatistic>>> = _wordsListLiveData

    fun getWordsByWordSetId(wordSetId: Long) = _wordsListLiveData.trackActionExecutionWithFlowResult {
        wordsRepository.getWordsByWordSetId(wordSetId)
    }

}