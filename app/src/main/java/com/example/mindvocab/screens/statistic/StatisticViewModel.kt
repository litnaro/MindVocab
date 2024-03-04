package com.example.mindvocab.screens.statistic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.model.AppException
import com.example.mindvocab.model.ErrorResult
import com.example.mindvocab.model.PendingResult
import com.example.mindvocab.model.Result
import com.example.mindvocab.model.SuccessResult
import com.example.mindvocab.model.achievement.AchievementsRepository
import com.example.mindvocab.model.achievement.entities.Achievement
import kotlinx.coroutines.launch

class StatisticViewModel(
    private val achievementsRepository: AchievementsRepository
) : BaseViewModel() {

    private val _achievements = MutableLiveData<Result<List<Achievement>>>(PendingResult())
    val achievements: LiveData<Result<List<Achievement>>> = _achievements

    init {
        getAchievements()
    }

    private fun getAchievements() {
        viewModelScope.launch {
            try {
                achievementsRepository.getAchievementsListWithAccountProgress().collect {
                    _achievements.value = SuccessResult(it)
                }
            } catch (e: AppException) {
                _achievements.value = ErrorResult(e)
            }
        }
    }


}