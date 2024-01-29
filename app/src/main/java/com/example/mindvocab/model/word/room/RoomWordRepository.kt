package com.example.mindvocab.model.word.room

import com.example.mindvocab.model.word.WordRepository
import com.example.mindvocab.model.word.entities.Word
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


enum class LanguageFilter(val mode: Long) {
    ENGLISH(0),
    RUSSIAN(1),
    UKRAINIAN(2),
}

class RoomWordRepository(
    private val wordsDao: WordsDao
) : WordRepository {

    override suspend fun getWordsByWordSetId(wordSetId: Long): Flow<List<Word>> {
        return wordsDao.getWordsByWordSetId(wordSetId)
            .map { entities ->
                entities.map {
                    it.toWord()
                }
            }
    }

    override suspend fun getWordToLearn(languageFilter: LanguageFilter): Flow<Word> {
        return wordsDao.getWordToLearn().map {
            Word(
                id = it.word.id,
                word = it.word.word,
                audio = "",
                image = it.word.image,
                exampleList = it.example.map { entity -> entity.sentence },
                explanation = it.word.explanation,
                transcription = it.word.transcription,
                translation = it.translation.map { entity -> entity.translation }.toString()
            )
        }
    }

    override suspend fun onWordKnown(word: Word) {
        // TODO("Not yet implemented")
    }

    override suspend fun onWordToLearn(word: Word) {
        // TODO("Not yet implemented")
    }
}