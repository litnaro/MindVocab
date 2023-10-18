package com.example.mindvocab.model.achievement

data class Achievement(
    val id: Int,
    val title: String,
    val description: String,
    val icon: String,
    val progress: Int
)
