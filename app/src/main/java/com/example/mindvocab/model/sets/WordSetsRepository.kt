package com.example.mindvocab.model.sets

import com.example.mindvocab.model.Repository
import com.example.mindvocab.model.sets.entity.WordSet
import com.example.mindvocab.model.sets.room.entity.WordSetDbEntity
import kotlinx.coroutines.flow.Flow

interface WordSetsRepository : Repository {

    /**
     * Get list of word sets.
     * @param filter filtering list by category or order. Getting all word sets by default
     * @throws StorageException
     */
    suspend fun getWordSets(filter: WordSetFilter = WordSetFilter.ALL) : Flow<List<WordSet>>

    /**
     * Create a new word set if it does not exist.
     * @throws StorageException
     * @throws WordSetAlreadyExistsException
     */
    suspend fun createWordSet(wordSet: WordSetDbEntity) : Boolean

    /**
     *  Delete a custom user word set.
     *  Platform word sets can not be deleted.
     *  @throws StorageException
     */
    suspend fun deleteWordSet(wordSet: WordSetDbEntity) : Boolean

    /**
     *  Mark the specified word set as selected.
     *  Adding all words selected from word set to user learning words.
     *  After selection those words are displayed in learning screen.
     *  @throws StorageException
     */
    fun selectWordSet(wordSet: WordSet) : Boolean

    /**
     *  Mark the specified word set as unselected.
     *  Removing all word set words but save their progress.
     *  @throws StorageException
     */
    fun unselectWordSet(wordSet: WordSet) : Boolean

}