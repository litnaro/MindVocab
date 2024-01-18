package com.example.mindvocab.core

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mindvocab.App
import com.example.mindvocab.screens.learn.LearnWordViewModel
import com.example.mindvocab.screens.learn.wordset.WordSetsViewModel

class ViewModelFactory(
    private val app: App
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when(modelClass) {
            LearnWordViewModel::class.java -> {
                LearnWordViewModel(app.learningRepository)
            }
            WordSetsViewModel::class.java -> {
                WordSetsViewModel(app.wordSetRepository)
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModelClass")
            }
        }
        return viewModel as T
    }
}

fun Fragment.factory() = ViewModelFactory(requireContext().applicationContext as App)