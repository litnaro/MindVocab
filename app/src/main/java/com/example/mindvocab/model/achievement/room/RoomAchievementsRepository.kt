package com.example.mindvocab.model.achievement.room

import com.example.mindvocab.model.AuthException
import com.example.mindvocab.model.StorageException
import com.example.mindvocab.model.achievement.AchievementsRepository
import com.example.mindvocab.model.achievement.entities.Achievement
import com.example.mindvocab.model.achievement.room.entities.AccountAchievementProgressDbEntity
import com.example.mindvocab.model.achievement.room.entities.AchievementProgressAsCheckedTuple
import com.example.mindvocab.model.achievement.room.entities.UpdateAccountAchievementProgressTuple
import com.example.mindvocab.model.room.wrapSQLiteException
import com.example.mindvocab.model.settings.account.AccountSettings
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomAchievementsRepository @Inject constructor(
    private val achievementsDao: AchievementsDao,
    private val accountSettings: AccountSettings,
    private val ioDispatcher: CoroutineDispatcher
) : AchievementsRepository {

    override suspend fun increaseAchievementsProgressByAction(action: AchievementsRepository.AchievementAction) = wrapSQLiteException(ioDispatcher) {
        val accountId = accountSettings.getCurrentAccountId()
        if (accountId == AccountSettings.NO_ACCOUNT_ID) throw AuthException()

        val achievements = achievementsDao.getAchievementsWithProgressByType(accountId, action.value)
        achievements.forEach { achievement ->
            if (achievement.progress == null || achievement.dateAchieved == null) {
                achievementsDao.insertAccountAchievementProgress(
                    AccountAchievementProgressDbEntity(
                        accountId = accountId,
                        achievementId = achievement.achievement.id,
                        dateAchieved = if (achievement.achievement.progressToAchieve == 1) System.currentTimeMillis() else 0,
                        progress = 1,
                        isChecked = false,
                    )
                )
            } else {
                achievementsDao.updateAccountAchievementProgress(
                    UpdateAccountAchievementProgressTuple(
                        accountId = accountId,
                        achievementId = achievement.achievement.id,
                        dateAchieved = if (achievement.progress + 1 == achievement.achievement.progressToAchieve) {
                            System.currentTimeMillis()
                        } else {
                            achievement.dateAchieved
                        },
                        progress = achievement.progress + 1,
                    )
                )
            }
        }
    }

    override suspend fun decreaseAchievementsProgressByAction(action: AchievementsRepository.AchievementAction) = wrapSQLiteException(ioDispatcher) {
        val accountId = accountSettings.getCurrentAccountId()
        if (accountId == AccountSettings.NO_ACCOUNT_ID) throw AuthException()

        val achievements = achievementsDao.getAchievementsWithProgressByType(accountId, action.value)
            .map {
                if (it.progress == null || it.dateAchieved == null) throw StorageException()
                AccountAchievementProgressDbEntity(
                    accountId = accountId,
                    achievementId = it.achievement.id,
                    dateAchieved = if (it.progress - 1 < it.achievement.progressToAchieve) 0 else it.dateAchieved,
                    isChecked = false,
                    progress = it.progress - 1
                )
            }
        achievementsDao.updateAccountAchievementProgresses(achievements)
    }

    override fun getAchievementsListWithAccountProgress(): Flow<List<Achievement>> {
        val accountId = accountSettings.getCurrentAccountId()
        if (accountId == AccountSettings.NO_ACCOUNT_ID) throw AuthException()
        return achievementsDao.getAchievementsWithAccountProgress(accountId).map { entities ->
            entities.map { it.toAchievement() }
        }
    }

    override fun getRecentAchievementsCount(): Flow<Int> {
        val accountId = accountSettings.getCurrentAccountId()
        if (accountId == AccountSettings.NO_ACCOUNT_ID) throw AuthException()
        return achievementsDao.getRecentAchievementsCount(
            accountId
        )
    }

    override suspend fun setAchievementAsChecked(achievement: Achievement) = wrapSQLiteException(ioDispatcher) {
        val accountId = accountSettings.getCurrentAccountId()
        if (accountId == AccountSettings.NO_ACCOUNT_ID) throw AuthException()

        achievementsDao.updateAccountAchievementProgress(
            AchievementProgressAsCheckedTuple(
                accountId = accountId,
                achievementId = achievement.id,
                isChecked = true
            )
        )
    }

}