package com.example.mindvocab.model.sets

import com.example.mindvocab.model.AuthException
import com.example.mindvocab.model.Repository
import com.example.mindvocab.model.sets.entity.WordSet
import com.example.mindvocab.model.StorageException
import kotlinx.coroutines.flow.Flow

interface WordSetsRepository : Repository {

    /**
     * Get list of word sets.
     * @param searchQuery Searching word set by a specific name.
     * @param filter Filtering list by selection. Getting all word sets by default.
     * @throws StorageException
     */
    fun getWordSets(searchQuery: String, filter: WordSetFilter = WordSetFilter.ALL) : Flow<List<WordSet>>

    /**
     *  Mark the specified word set as selected.
     *  Adding all words selected from word set to account words progress.
     *  After selection those words are displayed in learning screen.
     *  @param wordSet Word set to be selected.
     *  @throws StorageException
     *  @throws AuthException
     */
    suspend fun selectWordSet(wordSet: WordSet)

    /**
     *  Mark the specified word set as unselected.
     *  Removing all word set words but save their progress in account progress.
     *  @param wordSet Word set to be unselected.
     *  @throws StorageException
     *  @throws AuthException
     */
    suspend fun unselectWordSet(wordSet: WordSet)

}