package com.example.mindvocab.model.achievement.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "achievements",
    foreignKeys = [
        ForeignKey(
            entity = AchievementTypesDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["achievement_type_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class AchievementDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String,
    @ColumnInfo(name = "achievement_type_id") val achievementTypeId: Long,
    val description: String,
    val image: String,
    @ColumnInfo(name = "progress_to_achieve") val progressToAchieve: Int
)
