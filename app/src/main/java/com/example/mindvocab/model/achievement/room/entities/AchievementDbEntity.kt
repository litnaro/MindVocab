package com.example.mindvocab.model.achievement.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "achievements"
)
data class AchievementDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String,
    val description: String,
    val image: String,
    @ColumnInfo(name = "progress_to_achieve") val progressToAchieve: Int
)
