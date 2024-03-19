package com.example.mindvocab.model.achievement.room.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.example.mindvocab.model.achievement.entities.Achievement

data class AccountAchievementProgressTuple(
    val id: Int,
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

