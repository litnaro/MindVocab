package com.example.mindvocab.screens.statistic.monthly

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.model.statistic.StatisticRepository
import com.example.mindvocab.model.statistic.entities.CalendarDayStatistic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.example.mindvocab.core.Result
import javax.inject.Inject

@HiltViewModel
class StatisticMonthlyViewModel @Inject constructor(
    private val statisticRepository: StatisticRepository
) : BaseViewModel() {

    private val _monthStatisticLiveDataResult = MutableLiveData<Result<List<CalendarDayStatistic>>>(Result.Pending)
    val monthStatisticLiveDataResult: LiveData<Result<List<CalendarDayStatistic>>> = _monthStatisticLiveDataResult

    fun getMonthStatistic(selectedMonth: Int) {
        viewModelScope.launch {
            _monthStatisticLiveDataResult.value = Result.Success(
                statisticRepository.getStatisticForMonthCalendar(selectedMonth)
            )
        }
    }

}