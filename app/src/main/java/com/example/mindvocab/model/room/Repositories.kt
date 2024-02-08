package com.example.mindvocab.model.room

import android.content.Context
import androidx.room.Room
import com.example.mindvocab.model.account.AccountsRepository
import com.example.mindvocab.model.account.room.RoomAccountsRepository
import com.example.mindvocab.model.sets.WordSetsRepository
import com.example.mindvocab.model.sets.room.RoomWordSetsRepository
import com.example.mindvocab.model.settings.AppSettings
import com.example.mindvocab.model.settings.SharedPreferencesAppSettings
import com.example.mindvocab.model.settings.account.SharedPreferencesAccountSettings
import com.example.mindvocab.model.settings.application.SharedPreferencesApplicationSettings
import com.example.mindvocab.model.settings.notifications.SharedPreferencesNotificationSettings
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

    val appSettings: AppSettings by lazy {
        SharedPreferencesAppSettings(
            SharedPreferencesAccountSettings(applicationContext),
            SharedPreferencesApplicationSettings(applicationContext),
            SharedPreferencesNotificationSettings(applicationContext)
        )
    }

    // Repositories

    val accountsRepository: AccountsRepository by lazy {
        RoomAccountsRepository(database.getAccountsDao())
    }

    val wordSetsRepository: WordSetsRepository by lazy {
        RoomWordSetsRepository(database.getWordSetsDao(), accountsRepository)
    }

    val wordsRepository: WordRepository by lazy {
        RoomWordRepository(database.getWordsDao(), accountsRepository)
    }

    // Entrance point in MainActivity onCreate
    fun init(context: Context) {
        applicationContext = context
    }
}