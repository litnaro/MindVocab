package com.example.mindvocab.model.achievement

import com.example.mindvocab.model.Repository
import com.example.mindvocab.model.achievement.entities.Achievement
import kotlinx.coroutines.flow.Flow

interface AchievementsRepository : Repository {

    enum class AchievementAction (val value: Long) {
        WORD_LEARN_ACTION(1),
        WORD_KNOWN_ACTION(2),
        WORD_SET_FINISHED_ACTION(3),
        DAYS_IN_A_ROW_ACTION(4)
    }

    suspend fun updateAchievementsByAction(action: AchievementAction)

    suspend fun getAchievementsListWithAccountProgress() : Flow<List<Achievement>>

}