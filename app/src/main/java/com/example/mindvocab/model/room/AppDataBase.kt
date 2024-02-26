package com.example.mindvocab.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mindvocab.model.account.room.AccountsDao
import com.example.mindvocab.model.account.room.entities.AccountDbEntity
import com.example.mindvocab.model.achievement.room.entities.AccountAchievementProgressDbEntity
import com.example.mindvocab.model.achievement.room.entities.AchievementDbEntity
import com.example.mindvocab.model.learning.room.LearningDao
import com.example.mindvocab.model.repeating.room.RepeatingDao
import com.example.mindvocab.model.sets.room.WordSetsDao
import com.example.mindvocab.model.sets.room.entity.AccountWordSetDbEntity
import com.example.mindvocab.model.sets.room.entity.WordSetDbEntity
import com.example.mindvocab.model.sets.room.entity.WordSetsWithStatisticDbView
import com.example.mindvocab.model.word.room.entities.AccountWordProgressDbEntity
import com.example.mindvocab.model.word.room.entities.ExampleDbEntity
import com.example.mindvocab.model.word.room.entities.LanguageDbEntity
import com.example.mindvocab.model.word.room.entities.TranslationDbEntity
import com.example.mindvocab.model.word.room.entities.WordDbEntity
import com.example.mindvocab.model.word.room.WordsDao

@Database(
    version = 1,
    entities = [
        AccountDbEntity::class,
        AccountAchievementProgressDbEntity::class,
        AchievementDbEntity::class,
        WordSetDbEntity::class,
        ExampleDbEntity::class,
        LanguageDbEntity::class,
        TranslationDbEntity::class,
        WordDbEntity::class,
        AccountWordSetDbEntity::class,
        AccountWordProgressDbEntity::class
    ],
    views = [
        WordSetsWithStatisticDbView::class
    ]
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getWordSetsDao() : WordSetsDao

    abstract fun getWordsDao() : WordsDao

    abstract fun getAccountsDao() : AccountsDao

    abstract fun getLearningDao() : LearningDao

    abstract fun getRepeatingDao() : RepeatingDao

}