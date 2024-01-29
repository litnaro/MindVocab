package com.example.mindvocab.model.word

import com.example.mindvocab.model.word.entities.Word
import com.example.mindvocab.model.word.room.LanguageFilter
import kotlinx.coroutines.flow.Flow

interface WordRepository {

    suspend fun getWordsByWordSetId(wordSetId: Long): Flow<List<Word>>

    suspend fun getWordToLearn(languageFilter: LanguageFilter = LanguageFilter.ENGLISH) : Flow<Word>

    suspend fun onWordKnown(word: Word)

    suspend fun onWordToLearn(word: Word)

}