package com.example.mindvocab

import android.app.Application
import androidx.room.Room
import com.example.mindvocab.model.room.AppDataBase
import com.example.mindvocab.model.sets.WordSetRepository
import com.example.mindvocab.model.sets.room.RoomWordSetRepository
import com.example.mindvocab.model.word.learning.room.RoomLearningRepository
import com.example.mindvocab.model.word.learning.LearningRepository

class App : Application() {
    lateinit var database: AppDataBase
    lateinit var wordSetRepository: WordSetRepository
    val learningRepository: LearningRepository = RoomLearningRepository()

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java,
            "database.db"
        ).build()
        wordSetRepository = RoomWordSetRepository(database.getWordSetsDao())
    }

}