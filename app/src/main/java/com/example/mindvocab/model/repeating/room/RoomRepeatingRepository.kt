package com.example.mindvocab.model.repeating.room

import android.util.Log
import com.example.mindvocab.model.AuthException
import com.example.mindvocab.model.NoWordsToRepeatException
import com.example.mindvocab.model.account.AccountsRepository
import com.example.mindvocab.model.repeating.RepeatingRepository
import com.example.mindvocab.model.repeating.room.entities.UpdateWordProgressAsForgottenTuple
import com.example.mindvocab.model.repeating.room.entities.UpdateWordProgressAsRememberedTuple
import com.example.mindvocab.model.word.WordCalculations
import com.example.mindvocab.model.word.entities.WordToRepeat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.withContext

class RoomRepeatingRepository(
    private val repeatingDao: RepeatingDao,
    private val accountsRepository: AccountsRepository,
    private val ioDispatcher: CoroutineDispatcher
) : RepeatingRepository {

    private val currentWordToRepeat = MutableSharedFlow<WordToRepeat>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override suspend fun listenWordToRepeat(): Flow<WordToRepeat> {
        return currentWordToRepeat
    }

    override suspend fun getWordToRepeat() = withContext(ioDispatcher) {
        accountsRepository.getAccount().collect { account ->
            if (account == null) throw AuthException()
            val wordToRepeat = repeatingDao.getWordForRepeating(account.id, 6, 1, WordCalculations.getStartOfTodayInMillis()) ?: throw NoWordsToRepeatException()
            currentWordToRepeat.emit(wordToRepeat.toRepeatingWord())
        }
    }

    override suspend fun onWordRemember(word: WordToRepeat) = withContext(ioDispatcher) {
        accountsRepository.getAccount().collect { account ->
            if (account == null) throw AuthException()
            Log.d("RoomRepeatingRepository", "onWordRemember")
            repeatingDao.updateWordProgressAsRemembered(
                UpdateWordProgressAsRememberedTuple(
                    accountId = account.id,
                    wordId = word.id,
                    timesRepeated = (word.timesRepeated + 1).toByte(),
                    lastRepeatedAt = System.currentTimeMillis()
                )
            )
            getWordToRepeat()
        }
    }

    override suspend fun onWordForgot(word: WordToRepeat) = withContext(ioDispatcher) {
        accountsRepository.getAccount().collect { account ->
            if (account == null) throw AuthException()
            Log.d("RoomRepeatingRepository", "onWordForgot")
            repeatingDao.updateWordProgressAsForgotten(
                UpdateWordProgressAsForgottenTuple(
                    accountId = account.id,
                    wordId = word.id,
                    lastRepeatedAt = System.currentTimeMillis()
                )
            )
            getWordToRepeat()
        }
    }

}