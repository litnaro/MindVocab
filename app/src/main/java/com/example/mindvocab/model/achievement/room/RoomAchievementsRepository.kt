package com.example.mindvocab.model.achievement.room

import com.example.mindvocab.model.AuthException
import com.example.mindvocab.model.account.AccountsRepository
import com.example.mindvocab.model.achievement.AchievementsRepository
import com.example.mindvocab.model.achievement.entities.Achievement
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

    override suspend fun getAchievementsListWithAccountProgress(): Flow<List<Achievement>> = withContext(ioDispatcher) {
        val account = accountsRepository.getAccount().first() ?: throw AuthException()
        achievementsDao.getAchievementsWithAccountProgress(account.id).map { entities ->
            entities.map { it.toAchievement() }
        }
    }
}