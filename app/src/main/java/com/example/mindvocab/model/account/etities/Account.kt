package com.example.mindvocab.model.account.etities

import com.example.mindvocab.model.sets.WordSet

data class Account(
    val id: Int,
    val username: String,
    val photo: String,
    val activeWordSets: List<WordSet>,
    val statistic: AccountStatistic
)
