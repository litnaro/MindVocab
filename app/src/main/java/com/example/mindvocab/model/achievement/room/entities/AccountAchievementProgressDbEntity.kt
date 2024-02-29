package com.example.mindvocab.model.achievement.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.example.mindvocab.model.account.room.entities.AccountDbEntity

@Entity(
    tableName = "accounts_achievements_progress",
    foreignKeys = [
        ForeignKey(
            entity = AccountDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["account_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = AchievementDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["achievement_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = AchievementTypesDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["achievement_type_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    primaryKeys = ["account_id", "achievement_id"],
    indices = [
        Index("achievement_id")
    ]
)
data class AccountAchievementProgressDbEntity(
    @ColumnInfo(name = "account_id") val accountId: Long,
    @ColumnInfo(name = "achievement_id") val achievementId: Long,
    @ColumnInfo(name = "achievement_type_id") val achievementTypeId: Long,
    val progress: Int,
    @ColumnInfo(name = "date_achieved") val dateAchieved: Long,
    @ColumnInfo(name = "is_checked") val isChecked: Boolean,
)
