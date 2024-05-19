package com.example.mindvocab.screens.statistic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.core.Result
import com.example.mindvocab.model.achievement.AchievementsRepository
import com.example.mindvocab.model.achievement.entities.Achievement
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticViewModel @Inject constructor(
    private val achievementsRepository: AchievementsRepository
) : BaseViewModel() {

    private val _achievementsLiveDataResult = MutableLiveData<Result<List<Achievement>>>(Result.Pending)
    val achievementsLiveDataResult: LiveData<Result<List<Achievement>>> = _achievementsLiveDataResult

    init {
        getAchievements()
    }

    fun setAchievementAsChecked(achievement: Achievement) {
        viewModelScope.launch {
            achievementsRepository.setAchievementAsChecked(achievement)
        }
    }

    private fun getAchievements() = _achievementsLiveDataResult.trackActionExecutionWithFlowResult {
        achievementsRepository.getAchievementsListWithAccountProgress()
    }

}