package com.example.mindvocab.model.achievement.room

import com.example.mindvocab.model.AuthException
import com.example.mindvocab.model.StorageException
import com.example.mindvocab.model.account.AccountsRepository
import com.example.mindvocab.model.achievement.AchievementsRepository
import com.example.mindvocab.model.achievement.entities.Achievement
import com.example.mindvocab.model.achievement.room.entities.AccountAchievementProgressDbEntity
import com.example.mindvocab.model.room.wrapSQLiteException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomAchievementsRepository @Inject constructor(
    private val achievementsDao: AchievementsDao,
    private val accountsRepository: AccountsRepository,
    private val ioDispatcher: CoroutineDispatcher
) : AchievementsRepository {

    override suspend fun increaseAchievementsProgressByAction(action: AchievementsRepository.AchievementAction) = wrapSQLiteException(ioDispatcher) {
        val account = accountsRepository.getAccount().first() ?: throw AuthException()

        val achievements = achievementsDao.getAchievementsWithProgressByType(account.id, action.value)
        achievements.forEach { achievement ->
            if (achievement.progress == null || achievement.dateAchieved == null) {
                achievementsDao.insertAccountAchievementProgress(
                    AccountAchievementProgressDbEntity(
                        accountId = account.id,
                        achievementId = achievement.achievement.id,
                        dateAchieved = if (achievement.achievement.progressToAchieve == 1) System.currentTimeMillis() else 0,
                        progress = 1,
                        isChecked = false,
                    )
                )
            } else {
                achievementsDao.updateAccountAchievementProgress(
                    AccountAchievementProgressDbEntity(
                        accountId = account.id,
                        achievementId = achievement.achievement.id,
                        dateAchieved = if (achievement.progress + 1 == achievement.achievement.progressToAchieve) {
                            System.currentTimeMillis()
                        } else {
                            achievement.dateAchieved
                        },
                        progress = achievement.progress + 1,
                        isChecked = false,
                    )
                )
            }
        }
    }

    override suspend fun decreaseAchievementsProgressByAction(action: AchievementsRepository.AchievementAction) = withContext(ioDispatcher) {
        val account = accountsRepository.getAccount().first() ?: throw AuthException()
        val achievements = achievementsDao.getAchievementsWithProgressByType(account.id, action.value)
            .map {
                if (it.progress == null || it.dateAchieved == null) throw StorageException()
                AccountAchievementProgressDbEntity(
                    accountId = account.id,
                    achievementId = it.achievement.id,
                    dateAchieved = if (it.progress - 1 < it.achievement.progressToAchieve) 0 else it.dateAchieved,
                    isChecked = false,
                    progress = it.progress - 1
                )
            }
        achievementsDao.updateAccountAchievementProgresses(achievements)
    }

    override suspend fun getAchievementsListWithAccountProgress(): Flow<List<Achievement>> = wrapSQLiteException(ioDispatcher) {
        val account = accountsRepository.getAccount().first() ?: throw AuthException()
        achievementsDao.getAchievementsWithAccountProgress(account.id).map { entities ->
            entities.map { it.toAchievement() }
        }
    }

    override suspend fun getRecentAchievementsCount(): Flow<Int> = wrapSQLiteException(ioDispatcher) {
        val account = accountsRepository.getAccount().first() ?: throw AuthException()
        achievementsDao.getRecentAchievementsCount(account.id)
    }

    override suspend fun setAchievementAsChecked(achievement: Achievement) {
        val account = accountsRepository.getAccount().first() ?: throw AuthException()
        achievementsDao.updateAccountAchievementProgress(
            AccountAchievementProgressDbEntity(
                accountId = account.id,
                achievementId = achievement.id,
                dateAchieved = achievement.dateAchieved,
                progress = achievement.progress,
                isChecked = true
            )
        )
    }

}