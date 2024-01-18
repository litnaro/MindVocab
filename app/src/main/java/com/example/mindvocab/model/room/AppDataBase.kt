package com.example.mindvocab.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mindvocab.model.account.room.entities.AccountDbEntity
import com.example.mindvocab.model.achievement.room.entities.AccountAchievementProgressDbEntity
import com.example.mindvocab.model.achievement.room.entities.AchievementDbEntity
import com.example.mindvocab.model.sets.room.WordSetsDao
import com.example.mindvocab.model.sets.room.entity.AccountWordSetDbEntity
import com.example.mindvocab.model.sets.room.entity.WordSetDbEntity
import com.example.mindvocab.model.word.AccountWordProgressDbEntity
import com.example.mindvocab.model.word.ExampleDbEntity
import com.example.mindvocab.model.word.LanguageDbEntity
import com.example.mindvocab.model.word.TranslationDbEntity
import com.example.mindvocab.model.word.WordDbEntity

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
    ]
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getWordSetsDao() : WordSetsDao

}