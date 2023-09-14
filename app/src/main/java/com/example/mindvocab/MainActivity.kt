package com.example.mindvocab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mindvocab.databinding.ActivityMainBinding
import com.example.mindvocab.screens.category.CategoryListFragment
import com.example.mindvocab.screens.home.LearnWordsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(binding.fragmentContainer.id, LearnWordsFragment())
            .commit()
    }
}