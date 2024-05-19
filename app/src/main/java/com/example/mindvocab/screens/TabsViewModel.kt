package com.example.mindvocab.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.model.achievement.AchievementsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TabsViewModel @Inject constructor(
    private val achievementsRepository: AchievementsRepository
) : BaseViewModel() {

    private val _recentAchievementsCountLiveData = MutableLiveData(0)
    val recentAchievementsCount: LiveData<Int> = _recentAchievementsCountLiveData

    init {
        viewModelScope.launch {
            achievementsRepository.getRecentAchievementsCount().collect {
                _recentAchievementsCountLiveData.value = it
            }
        }
    }
}