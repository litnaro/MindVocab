package com.example.mindvocab.model.sets

import com.example.mindvocab.model.Repository
import com.example.mindvocab.model.sets.entity.WordSet
import com.example.mindvocab.model.sets.room.entity.WordSetDbEntity
import kotlinx.coroutines.flow.Flow

interface WordSetRepository : Repository {

    suspend fun getWordSets() : Flow<List<WordSet>>

    suspend fun createWordSet(wordSet: WordSetDbEntity) : Boolean

    fun selectWordSetToLearn(wordSet: WordSet) : Boolean

    fun removeWordSetFromLearning(wordSet: WordSet) : Boolean

}