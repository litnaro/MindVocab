package com.example.mindvocab.screens.statistic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.model.AppException
import com.example.mindvocab.core.Result
import com.example.mindvocab.model.achievement.AchievementsRepository
import com.example.mindvocab.model.achievement.entities.Achievement
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticViewModel @Inject constructor(
    private val achievementsRepository: AchievementsRepository
) : BaseViewModel() {

    private val _achievements = MutableLiveData<Result<List<Achievement>>>(Result.Pending)
    val achievements: LiveData<Result<List<Achievement>>> = _achievements

    init {
        getAchievements()
    }

    fun setAchievementAsChecked(achievement: Achievement) {
        viewModelScope.launch {
            achievementsRepository.setAchievementAsChecked(achievement)
        }
    }

    private fun getAchievements() {
        viewModelScope.launch {
            delay(1500)
            try {
                achievementsRepository.getAchievementsListWithAccountProgress().collect {
                    _achievements.value = Result.Success(it)
                }
            } catch (e: AppException) {
                _achievements.value = Result.Error(e)
            }
        }
    }


}