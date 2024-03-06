package com.example.mindvocab.model.achievement.room

import com.example.mindvocab.model.AuthException
import com.example.mindvocab.model.account.AccountsRepository
import com.example.mindvocab.model.achievement.AchievementsRepository
import com.example.mindvocab.model.achievement.entities.Achievement
import com.example.mindvocab.model.achievement.room.entities.AccountAchievementProgressDbEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RoomAchievementsRepository(
    private val achievementsDao: AchievementsDao,
    private val accountsRepository: AccountsRepository,
    private val ioDispatcher: CoroutineDispatcher
) : AchievementsRepository {

    override suspend fun updateAchievementsByAction(action: AchievementsRepository.AchievementAction) = withContext(ioDispatcher) {
        val account = accountsRepository.getAccount().first() ?: throw AuthException()
        val achievements = achievementsDao.getAchievementsByType(account.id, action.value).map {
            AccountAchievementProgressDbEntity(
                accountId = account.id,
                achievementId = it.achievement.id,
                dateAchieved = if (it.progress + 1 == it.achievement.progressToAchieve) System.currentTimeMillis() else 0,
                progress = if (it.progress < it.achievement.progressToAchieve ) it.progress + 1 else it.progress,
                isChecked = false,
            )
        }
        achievementsDao.upsertAccountAchievement(achievements)
    }

    override suspend fun getAchievementsListWithAccountProgress(): Flow<List<Achievement>> = withContext(ioDispatcher) {
        val account = accountsRepository.getAccount().first() ?: throw AuthException()
        achievementsDao.getAchievementsWithAccountProgress(account.id).map { entities ->
            entities.map { it.toAchievement() }
        }
    }

}