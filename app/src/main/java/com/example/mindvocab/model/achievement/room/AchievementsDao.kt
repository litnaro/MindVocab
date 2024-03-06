package com.example.mindvocab.model.achievement.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.mindvocab.model.achievement.room.entities.AccountAchievementProgressDbEntity
import com.example.mindvocab.model.achievement.room.entities.AccountAchievementProgressTuple
import com.example.mindvocab.model.achievement.room.entities.AchievementProgressTuple
import kotlinx.coroutines.flow.Flow

@Dao
interface AchievementsDao {

    @Query("SELECT\n" +
            "    achievements.id,\n" +
            "    achievements.title,\n" +
            "    achievements.description,\n" +
            "    achievements.image,\n" +
            "    IFNULL(accounts_achievements_progress.progress, 0) AS progress,\n" +
            "    achievements.progress_to_achieve,\n" +
            "    IFNULL(accounts_achievements_progress.date_achieved, 0) AS date_achieved,\n" +
            "    IFNULL(accounts_achievements_progress.is_checked, 0) AS is_checked\n" +
            "FROM\n" +
            "    achievements\n" +
            "LEFT JOIN\n" +
            "    accounts_achievements_progress ON accounts_achievements_progress.account_id = :accountId \n" +
            "                                     AND accounts_achievements_progress.achievement_id = achievements.id;\n")
    fun getAchievementsWithAccountProgress(accountId: Long) : Flow<List<AccountAchievementProgressTuple>>

    @Query("SELECT \n" +
            "    achievements.*, \n" +
            "    IFNULL(\n" +
            "        (\n" +
            "            SELECT accounts_achievements_progress.progress \n" +
            "            FROM accounts_achievements_progress \n" +
            "            WHERE accounts_achievements_progress.achievement_id = achievements.id \n" +
            "            AND accounts_achievements_progress.account_id = :accountId\n" +
            "        ), \n" +
            "        0\n" +
            "    ) AS progress\n" +
            "FROM achievements\n" +
            "WHERE achievements.achievement_type_id = :achievementType;\n")
    fun getAchievementsByType(accountId: Long, achievementType: Long) : List<AchievementProgressTuple>

    @Upsert
    fun upsertAccountAchievement(achievementProgress: List<AccountAchievementProgressDbEntity>)

}