package com.example.mindvocab.screens.statistic.metric

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.model.statistic.StatisticRepository
import com.example.mindvocab.model.statistic.entities.AchievementsStatistic
import com.example.mindvocab.model.statistic.entities.WordsStatistic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticMetricViewModel @Inject constructor(
    private val statisticRepository: StatisticRepository
): BaseViewModel() {

    private val _wordsStatistic = MutableLiveData<WordsStatistic>()
    val wordsStatistic: LiveData<WordsStatistic> = _wordsStatistic

    private val _achievementsStatistic = MutableLiveData<AchievementsStatistic>()
    val achievementsStatistic: LiveData<AchievementsStatistic> = _achievementsStatistic

    private val _wordSetsStatistic = MutableLiveData<List<String>>()
    val wordSetsStatistic: LiveData<List<String>> = _wordSetsStatistic

    init {
        getWordsStatistic()
        getAchievementsStatistic()
        getWordSetsStatistic()
    }


    private fun getWordsStatistic() {
        viewModelScope.launch {
            statisticRepository.getWordsStatistic().collect {
                _wordsStatistic.value = it
            }
        }
    }

    private fun getAchievementsStatistic() {
        viewModelScope.launch {
            statisticRepository.getAchievementStatistic().collect {
                _achievementsStatistic.value = it
            }
        }
    }

    private fun getWordSetsStatistic() {
        viewModelScope.launch {
            statisticRepository.getWordSetsStatistic().collect {
                _wordSetsStatistic.value = it
            }
        }
    }

}