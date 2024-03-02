package com.example.mindvocab.model.achievement.room

import androidx.room.Dao
import androidx.room.Query
import com.example.mindvocab.model.achievement.room.entities.AccountAchievementProgressTuple
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

}