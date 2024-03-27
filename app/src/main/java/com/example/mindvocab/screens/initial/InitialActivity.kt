package com.example.mindvocab.screens.initial

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mindvocab.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InitialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial)
    }
}