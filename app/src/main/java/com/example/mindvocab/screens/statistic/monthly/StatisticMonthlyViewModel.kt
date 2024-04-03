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

    private val _monthStatistic = MutableLiveData<Result<List<CalendarDayStatistic>>>(Result.PendingResult())
    val monthStatistic: LiveData<Result<List<CalendarDayStatistic>>> = _monthStatistic

    fun getMonthStatistic(selectedMonth: Int) {
        viewModelScope.launch {
            _monthStatistic.value = Result.SuccessResult(
                statisticRepository.getStatisticForMonthCalendar(selectedMonth)
            )
        }
    }

}