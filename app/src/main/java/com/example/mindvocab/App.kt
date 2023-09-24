package com.example.mindvocab

import android.app.Application
import com.example.mindvocab.model.sets.WordSetDataRepository

class App : Application() {
    val wordSetRepository = WordSetDataRepository()
}