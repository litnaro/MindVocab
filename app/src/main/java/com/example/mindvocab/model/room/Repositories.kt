package com.example.mindvocab.model.room

import android.content.Context
import androidx.room.Room
import com.example.mindvocab.model.account.AccountsRepository
import com.example.mindvocab.model.account.room.RoomAccountsRepository
import com.example.mindvocab.model.learning.LearningRepository
import com.example.mindvocab.model.learning.room.RoomLearningRepository
import com.example.mindvocab.model.sets.WordSetsRepository
import com.example.mindvocab.model.sets.room.RoomWordSetsRepository
import com.example.mindvocab.model.settings.AppSettingsManager
import com.example.mindvocab.model.settings.SharedPreferencesAppSettingsManager
import com.example.mindvocab.model.settings.account.SharedPreferencesAccountSettings
import com.example.mindvocab.model.settings.application.SharedPreferencesApplicationSettings
import com.example.mindvocab.model.settings.learn.SharedPreferencesLearnSettings
import com.example.mindvocab.model.settings.notifications.SharedPreferencesNotificationSettings
import com.example.mindvocab.model.settings.repeat.SharedPreferencesRepeatSettings
import com.example.mindvocab.model.word.WordRepository
import com.example.mindvocab.model.word.room.RoomWordRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object Repositories {

    // Additional dependencies

    private lateinit var applicationContext: Context

    private val database: AppDataBase by lazy {
        Room.databaseBuilder( applicationContext, AppDataBase::class.java, "database.db")
            .createFromAsset("MindVocabData.db")
            .build()
    }

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    //Settings

    val appSettingsManager: AppSettingsManager by lazy {
        SharedPreferencesAppSettingsManager(
            SharedPreferencesAccountSettings(applicationContext),
            SharedPreferencesApplicationSettings(applicationContext),
            SharedPreferencesNotificationSettings(applicationContext),
            SharedPreferencesLearnSettings(applicationContext),
            SharedPreferencesRepeatSettings(applicationContext)
        )
    }

    // Repositories

    val accountsRepository: AccountsRepository by lazy {
        RoomAccountsRepository(database.getAccountsDao())
    }

    val wordSetsRepository: WordSetsRepository by lazy {
        RoomWordSetsRepository(database.getWordSetsDao(), database.getWordsDao(), accountsRepository, ioDispatcher)
    }

    val wordsRepository: WordRepository by lazy {
        RoomWordRepository(database.getWordsDao(), accountsRepository)
    }

    val learningRepository: LearningRepository by lazy {
        RoomLearningRepository(database.getLearningDao(), accountsRepository, appSettingsManager.getApplicationSettings(), ioDispatcher)
    }

    // Entrance point in MainActivity onCreate
    fun init(context: Context) {
        applicationContext = context
    }
}