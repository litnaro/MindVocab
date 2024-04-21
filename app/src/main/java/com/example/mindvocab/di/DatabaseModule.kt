package com.example.mindvocab.di

import android.content.Context
import androidx.room.Room
import com.example.mindvocab.model.account.room.AccountsDao
import com.example.mindvocab.model.achievement.room.AchievementsDao
import com.example.mindvocab.model.learning.room.LearningDao
import com.example.mindvocab.model.repeating.room.RepeatingDao
import com.example.mindvocab.model.room.AppDataBase
import com.example.mindvocab.model.sets.room.WordSetsDao
import com.example.mindvocab.model.statistic.room.StatisticDao
import com.example.mindvocab.model.word.room.WordsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideIoDispatcher() : CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context) : AppDataBase {
        return Room.databaseBuilder(appContext, AppDataBase::class.java, "database.db")
            .createFromAsset("MindVocabDb.db")
            .build()
    }

    @Provides
    fun provideAccountsDao(database: AppDataBase) : AccountsDao {
        return database.getAccountsDao()
    }

    @Provides
    fun provideWordsDao(database: AppDataBase) : WordsDao {
        return database.getWordsDao()
    }

    @Provides
    fun provideAchievementsDao(database: AppDataBase) : AchievementsDao {
        return database.getAchievementsDao()
    }

    @Provides
    fun provideLearningDao(database: AppDataBase) : LearningDao {
        return database.getLearningDao()
    }

    @Provides
    fun provideRepeatingDao(database: AppDataBase) : RepeatingDao {
        return database.getRepeatingDao()
    }

    @Provides
    fun provideWordSetsDao(database: AppDataBase) : WordSetsDao {
        return database.getWordSetsDao()
    }

    @Provides
    fun provideStatisticDao(database: AppDataBase) : StatisticDao {
        return database.getStatisticDao()
    }
}