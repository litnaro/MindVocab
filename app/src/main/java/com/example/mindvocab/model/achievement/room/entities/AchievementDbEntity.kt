package com.example.mindvocab.model.achievement.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "achievements"
)
data class AchievementDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String,
    val description: String,
    val image: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AchievementDbEntity

        if (id != other.id) return false
        if (title != other.title) return false
        if (description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + description.hashCode()
        return result
    }
}
