package com.example.mindvocab.model.word

import com.example.mindvocab.model.Repository
import com.example.mindvocab.model.word.entities.WordStatistic
import kotlinx.coroutines.flow.Flow

interface WordRepository : Repository {

    /**
     * Gets words with translation and progress by specific word set id.
     * Pack those words to WordStatistic entity.
     * @param wordSetId Used to clarify witch word set words to get.
     * @return List of words statistic as flow.
     */
    suspend fun getWordsByWordSetId(wordSetId: Long): Flow<List<WordStatistic>>

}