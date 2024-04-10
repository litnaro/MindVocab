package com.example.mindvocab.model.achievement

import com.example.mindvocab.model.AuthException
import com.example.mindvocab.model.Repository
import com.example.mindvocab.model.StorageException
import com.example.mindvocab.model.achievement.entities.Achievement
import kotlinx.coroutines.flow.Flow

interface AchievementsRepository : Repository {

    //TODO finished word set and day in a row achievements
    @Suppress("unused")
    enum class AchievementAction (val value: Long) {
        WORD_LEARN_ACTION(1),
        WORD_KNOWN_ACTION(2),
        WORD_SET_FINISHED_ACTION(3),
        DAYS_IN_A_ROW_ACTION(4)
    }

    /**
     * Call when any action from AchievementAction occurs.
     * Take all achievements of specific type [action] from database with account progress and date achieved.
     * Increase progress for each such achievement.
     *
     * If account has no progress or specific action happened for the first time - creates achievements entities for that account
     * and set achievements progress as 1 and in case progress is max value for that achievement set date of achieved as now else set 0.
     *
     * If account already has progress increase it`s progress and in case progress is max value for that achievement set date of achieved as now,
     * else set previous achievement date.
     * Setting previous achievement date needed because of Return Previous Word application feature, so account cannot cheat with achievements.
     *
     * @param action Identified achievements which progress need to be increased.
     * @throws StorageException
     * @throws AuthException
     */
    suspend fun increaseAchievementsProgressByAction(action: AchievementAction)

    /**
     * Call when previous learning/repeating word needs undone, so user could choose another option.
     * Take all achievements of specific type [action] from database with account progress and date achieved.
     * Decrease progress for each such achievement.
     *
     * If progress of the achievement is less then it`s needed to be to complete the achievement,
     * updates "date achieved" by setting 0.
     *
     * @param action Identified achievements which progress need to be decreased.
     * @throws StorageException
     * @throws AuthException
     */
    suspend fun decreaseAchievementsProgressByAction(action: AchievementAction)

    /**
     * Gets list of achievements with account progress.
     * @throws StorageException
     * @throws AuthException
     * @return Flow of achievements with all available data.
     */
    suspend fun getAchievementsListWithAccountProgress() : Flow<List<Achievement>>

    /**
     * Gets count of unchecked achievements to notify
     * user about achievement has achieved.
     * @throws AuthException
     * @throws StorageException
     * @return Flow of new achievements count.
     */
    suspend fun getRecentAchievementsCount() : Flow<Int>

    /**
     * Shows that user has seen that specific [achievement]
     * has achieved.
     * @throws StorageException
     * @throws AuthException
     */
    suspend fun setAchievementAsChecked(achievement: Achievement)

}