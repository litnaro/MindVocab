package com.example.mindvocab.model.achievement.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "achievements_types"
)
data class AchievementTypesDbEntity (
    @PrimaryKey(autoGenerate = true) val id: Long,
    val type: String,
)