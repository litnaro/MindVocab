package com.example.mindvocab.model.achievement.entities

data class Achievement(
    val id: Long,
    val title: String,
    val description: String,
    val image: String,
    val progress: Int,
    val maxProgress: Int,
    val dateAchieved: Long,
    val isChecked: Boolean
)
