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

    private val _startedWordsLiveData = MutableLiveData<List<WordDayStatistic>>()
    val startedWordsLiveData: LiveData<List<WordDayStatistic>> = _startedWordsLiveData

    private val _repeatedWordsLiveData = MutableLiveData<List<WordDayStatistic>>()
    val repeatedWordsLiveData: LiveData<List<WordDayStatistic>> = _repeatedWordsLiveData

    fun getStartedWords(date: Long) {
        viewModelScope.launch {
            _startedWordsLiveData.value = statisticRepository.getStartedWordsDetailForDay(date)
        }
    }

    fun getRepeatedWords(date: Long) {
        viewModelScope.launch {
            _repeatedWordsLiveData.value = statisticRepository.getRepeatedWordsDetailForDay(date)
        }
    }

}