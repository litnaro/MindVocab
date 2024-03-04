package com.example.mindvocab.model.achievement

import com.example.mindvocab.model.Repository
import com.example.mindvocab.model.achievement.entities.Achievement
import kotlinx.coroutines.flow.Flow

interface AchievementsRepository : Repository {

    suspend fun getAchievementsListWithAccountProgress() : Flow<List<Achievement>>

}