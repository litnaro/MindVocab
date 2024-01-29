package com.example.mindvocab.model.sets.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mindvocab.model.sets.entity.WordSet

@Entity(
    tableName = "word_sets"
)
data class WordSetDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val image: String
) {

    fun toWordSet() : WordSet = WordSet(
        id = id,
        name = name,
        photo = "photo",
        isSelected = false,
        wordsCount = 100,
        accountCompletedWordsCount = 50
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WordSetDbEntity

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}
