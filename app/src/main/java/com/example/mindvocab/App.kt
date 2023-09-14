package com.example.mindvocab

import android.app.Application
import com.example.mindvocab.model.caregory.CategoryDataRepository

class App : Application() {

    val categoryDataRepository = CategoryDataRepository()
}