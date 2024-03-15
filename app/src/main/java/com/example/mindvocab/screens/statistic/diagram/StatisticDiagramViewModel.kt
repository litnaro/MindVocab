package com.example.mindvocab.screens.statistic.diagram

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.model.AppException
import com.example.mindvocab.core.Result
import com.example.mindvocab.model.statistic.StatisticRepository
import com.example.mindvocab.model.statistic.entities.WordsStatisticPercentage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticDiagramViewModel @Inject constructor(
    private val statisticRepository: StatisticRepository
) : BaseViewModel() {

    private val _statistic = MutableLiveData<Result<WordsStatisticPercentage>>(Result.PendingResult())
    val statistic: LiveData<Result<WordsStatisticPercentage>> = _statistic

    init {
        getStatistic()
    }

    private fun getStatistic() {
        viewModelScope.launch {
            try {
                statisticRepository.getWordsStatisticPercentage().collect {
                    _statistic.value = Result.SuccessResult(it)
                }
            } catch (e: AppException) {
                _statistic.value = Result.ErrorResult(e)
            }
        }
    }

}