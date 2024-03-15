@file:Suppress("unused")

package com.example.mindvocab.di

import com.example.mindvocab.model.account.AccountsRepository
import com.example.mindvocab.model.account.room.RoomAccountsRepository
import com.example.mindvocab.model.achievement.AchievementsRepository
import com.example.mindvocab.model.achievement.room.RoomAchievementsRepository
import com.example.mindvocab.model.learning.LearningRepository
import com.example.mindvocab.model.learning.room.RoomLearningRepository
import com.example.mindvocab.model.repeating.RepeatingRepository
import com.example.mindvocab.model.repeating.room.RoomRepeatingRepository
import com.example.mindvocab.model.sets.WordSetsRepository
import com.example.mindvocab.model.sets.room.RoomWordSetsRepository
import com.example.mindvocab.model.statistic.StatisticRepository
import com.example.mindvocab.model.statistic.room.RoomStatisticRepository
import com.example.mindvocab.model.word.WordsRepository
import com.example.mindvocab.model.word.room.RoomWordsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAccountsRepository(
        accountsRepository: RoomAccountsRepository
    ) : AccountsRepository

    @Binds
    @Singleton
    abstract fun bindAchievementsRepository(
        achievementsRepository: RoomAchievementsRepository
    ) : AchievementsRepository

    @Binds
    @Singleton
    abstract fun bindLearningRepository(
        learningRepository: RoomLearningRepository
    ) : LearningRepository

    @Binds
    @Singleton
    abstract fun bindRepeatingRepository(
        repeatingRepository: RoomRepeatingRepository
    ) : RepeatingRepository

    @Binds
    @Singleton
    abstract fun bindWordSetsRepository(
        wordSetsRepository: RoomWordSetsRepository
    ) : WordSetsRepository

    @Binds
    @Singleton
    abstract fun bindStatisticRepository(
        statisticRepository: RoomStatisticRepository
    ) : StatisticRepository

    @Binds
    @Singleton
    abstract fun bindWordsRepository(
        wordsRepository: RoomWordsRepository
    ) : WordsRepository

}