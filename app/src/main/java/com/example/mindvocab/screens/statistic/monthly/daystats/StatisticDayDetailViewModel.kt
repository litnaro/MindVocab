package com.example.mindvocab.screens.statistic.monthly.daystats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.model.statistic.StatisticRepository
import com.example.mindvocab.model.statistic.entities.WordDayStatistic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticDayDetailViewModel @Inject constructor(
    private val statisticRepository: StatisticRepository
) : BaseViewModel() {

    private val _startedWords = MutableLiveData<List<WordDayStatistic>>()
    val startedWords: LiveData<List<WordDayStatistic>> = _startedWords

    private val _repeatedWords = MutableLiveData<List<WordDayStatistic>>()
    val repeatedWords: LiveData<List<WordDayStatistic>> = _repeatedWords

    fun getStartedWords(date: Long) {
        viewModelScope.launch {
            _startedWords.value = statisticRepository.getStartedWordsDetailForDay(date)
        }
    }

    fun getRepeatedWords(date: Long) {
        viewModelScope.launch {
            _repeatedWords.value = statisticRepository.getRepeatedWordsDetailForDay(date)
        }
    }

}