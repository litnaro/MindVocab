package com.example.mindvocab.model.repeating

import com.example.mindvocab.model.Repository
import com.example.mindvocab.model.word.entities.WordToRepeat
import kotlinx.coroutines.flow.Flow

interface RepeatingRepository : Repository {

    suspend fun getWordsToRepeat() : Flow<List<WordToRepeat>>

    suspend fun listenWordToRepeat() : Flow<WordToRepeat>

    suspend fun getWordToRepeat()

    suspend fun onWordRemember(word: WordToRepeat)

    suspend fun onWordForgot(word: WordToRepeat)

}