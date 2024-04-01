package com.example.mindvocab.model.achievement.room.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.PrimaryKey
import com.example.mindvocab.model.achievement.entities.Achievement

data class AccountAchievementProgressTuple(
    val id: Long,
    val title: String,
    val description: String,
    val image: String,
    val progress: Int,
    @ColumnInfo(name = "progress_to_achieve") val progressToAchieve: Int,
    @ColumnInfo(name = "date_achieved") val dateAchieved: Long,
    @ColumnInfo(name = "is_checked") val isChecked: Boolean
) {
    fun toAchievement() = Achievement(
        id, title, description, image, progress, progressToAchieve, dateAchieved, isChecked
    )
}

data class AchievementProgressToUpdateTuple(
    @Embedded val achievement: AchievementDbEntity,
    val progress: Int?,
    @ColumnInfo(name = "date_achieved") val dateAchieved: Long?
)

data class UpdateAccountAchievementProgressTuple(
    @ColumnInfo(name = "account_id") @PrimaryKey val accountId: Long,
    @ColumnInfo(name = "achievement_id") @PrimaryKey val achievementId: Long,
    @ColumnInfo(name = "progress") val progress: Int,
    @ColumnInfo(name = "date_achieved") val dateAchieved: Long,
)

data class AchievementProgressAsCheckedTuple(
    @ColumnInfo(name = "account_id") @PrimaryKey val accountId: Long,
    @ColumnInfo(name = "achievement_id") @PrimaryKey val achievementId: Long,
    @ColumnInfo(name = "is_checked") val isChecked: Boolean
)

