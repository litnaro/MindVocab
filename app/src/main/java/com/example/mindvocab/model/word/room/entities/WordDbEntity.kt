package com.example.mindvocab.model.word.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.mindvocab.model.sets.room.entity.WordSetDbEntity
import com.example.mindvocab.model.word.entities.Word

@Entity(
    tableName = "words",
    foreignKeys = [
        ForeignKey(
            entity = WordSetDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["word_set_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("word_set_id")
    ]
)
data class WordDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val word: String,
    val image: String,
    val audio: String,
    val transcription: String,
    val explanation: String,
    @ColumnInfo(name = "word_set_id") val wordSetId: Long
) {
    fun toWord() : Word = Word(
        id = id,
        word = word,
        image = image,
        audio = audio,
        transcription = transcription,
        explanation = explanation,
        translation = "",
        exampleList = emptyList()
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WordDbEntity

        if (id != other.id) return false
        if (word != other.word) return false
        if (audio != other.audio) return false
        if (transcription != other.transcription) return false
        if (explanation != other.explanation) return false
        if (wordSetId != other.wordSetId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + word.hashCode()
        result = 31 * result + audio.hashCode()
        result = 31 * result + transcription.hashCode()
        result = 31 * result + explanation.hashCode()
        result = 31 * result + wordSetId.hashCode()
        return result
    }
}