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

    private val _wordsStatisticLiveData = MutableLiveData<WordsStatistic>()
    val wordsStatisticLiveData: LiveData<WordsStatistic> = _wordsStatisticLiveData

    private val _achievementsStatisticLiveData = MutableLiveData<AchievementsStatistic>()
    val achievementsStatisticLiveData: LiveData<AchievementsStatistic> = _achievementsStatisticLiveData

    private val _wordSetsStatisticLiveData = MutableLiveData<List<String>>()
    val wordSetsStatisticLiveData: LiveData<List<String>> = _wordSetsStatisticLiveData

    init {
        getWordsStatistic()
        getAchievementsStatistic()
        getWordSetsStatistic()
    }

    private fun getWordsStatistic() {
        viewModelScope.launch {
            statisticRepository.getWordsStatistic().collect {
                _wordsStatisticLiveData.value = it
            }
        }
    }

    private fun getAchievementsStatistic() {
        viewModelScope.launch {
            statisticRepository.getAchievementStatistic().collect {
                _achievementsStatisticLiveData.value = it
            }
        }
    }

    private fun getWordSetsStatistic() {
        viewModelScope.launch {
            statisticRepository.getWordSetsStatistic().collect {
                _wordSetsStatisticLiveData.value = it
            }
        }
    }

}