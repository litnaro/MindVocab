package com.example.mindvocab.screens.statistic.diagram

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.core.Result
import com.example.mindvocab.model.statistic.StatisticRepository
import com.example.mindvocab.model.statistic.entities.WordsStatisticPercentage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticDiagramViewModel @Inject constructor(
    private val statisticRepository: StatisticRepository
) : BaseViewModel() {

    private val _statisticLiveDataResult = MutableLiveData<Result<WordsStatisticPercentage>>(Result.Pending)
    val statisticLiveDataResult: LiveData<Result<WordsStatisticPercentage>> = _statisticLiveDataResult

    init {
        getStatistic()
    }

    private fun getStatistic() = _statisticLiveDataResult.trackActionExecutionWithFlowResult {
        statisticRepository.getWordsStatisticPercentage()
    }

}