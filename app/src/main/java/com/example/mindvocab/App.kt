package com.example.mindvocab

import android.app.Application
import com.example.mindvocab.model.sets.WordSetRepository
import com.example.mindvocab.model.studing.LearningRepository
import com.example.mindvocab.model.studing.LearningSource

class App : Application() {
    val wordSetRepository = WordSetRepository()
    val learningSource: LearningSource = LearningRepository()
}