package com.example.mindvocab.core

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mindvocab.App
import com.example.mindvocab.model.room.Repositories
import com.example.mindvocab.screens.learn.LearnWordViewModel
import com.example.mindvocab.screens.learn.wordset.WordSetsViewModel
import com.example.mindvocab.screens.settings.account.SettingsAccountViewModel
import com.example.mindvocab.screens.settings.account.edit.AccountEditViewModel
import com.example.mindvocab.screens.settings.application.SettingsApplicationViewModel
import com.example.mindvocab.screens.settings.notifications.SettingsNotificationsViewModel
import com.example.mindvocab.screens.settings.repeat.SettingsRepeatViewModel
import com.example.mindvocab.screens.word.WordsViewModel

class ViewModelFactory(
    private val app: App
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when(modelClass) {
            LearnWordViewModel::class.java -> {
                LearnWordViewModel(Repositories.wordsRepository)
            }
            WordSetsViewModel::class.java -> {
                WordSetsViewModel(Repositories.wordSetsRepository)
            }
            WordsViewModel::class.java -> {
                WordsViewModel(Repositories.wordsRepository)
            }
            SettingsAccountViewModel::class.java -> {
                SettingsAccountViewModel(Repositories.accountsRepository)
            }
            SettingsApplicationViewModel::class.java -> {
                SettingsApplicationViewModel(Repositories.appSettings.getApplicationSettings())
            }
            SettingsNotificationsViewModel::class.java -> {
                SettingsNotificationsViewModel(Repositories.appSettings.getNotificationSettings())
            }
            AccountEditViewModel::class.java -> {
                AccountEditViewModel(Repositories.accountsRepository)
            }
            SettingsRepeatViewModel::class.java -> {
                SettingsRepeatViewModel(Repositories.appSettings.getRepeatSettings())
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModelClass")
            }
        }
        return viewModel as T
    }
}

fun Fragment.factory() = ViewModelFactory(requireContext().applicationContext as App)