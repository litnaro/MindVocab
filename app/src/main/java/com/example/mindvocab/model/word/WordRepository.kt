package com.example.mindvocab.model.word

import com.example.mindvocab.model.word.entities.Word
import com.example.mindvocab.model.word.entities.WordStatistic
import kotlinx.coroutines.flow.Flow

interface WordRepository {

    suspend fun getWordsByWordSetId(wordSetId: Long): Flow<List<WordStatistic>>

    suspend fun listenWordToLearn() : Flow<Word>

    suspend fun getWordToLearn()

    suspend fun onWordKnown(word: Word)

    suspend fun onWordToLearn(word: Word)

}