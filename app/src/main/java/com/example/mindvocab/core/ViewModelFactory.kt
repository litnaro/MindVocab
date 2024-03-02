package com.example.mindvocab.core

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mindvocab.App
import com.example.mindvocab.model.room.Repositories
import com.example.mindvocab.screens.learn.LearnWordViewModel
import com.example.mindvocab.screens.learn.wordset.WordSetsViewModel
import com.example.mindvocab.screens.repeat.RepeatWordViewModel
import com.example.mindvocab.screens.repeat.list.RepeatingWordsViewModel
import com.example.mindvocab.screens.settings.account.SettingsAccountViewModel
import com.example.mindvocab.screens.settings.account.edit.AccountEditViewModel
import com.example.mindvocab.screens.settings.application.SettingsApplicationViewModel
import com.example.mindvocab.screens.settings.learn.SettingsLearnViewModel
import com.example.mindvocab.screens.settings.notifications.SettingsNotificationsViewModel
import com.example.mindvocab.screens.settings.repeat.SettingsRepeatViewModel
import com.example.mindvocab.screens.statistic.StatisticViewModel
import com.example.mindvocab.screens.statistic.diagram.StatisticDiagramViewModel
import com.example.mindvocab.screens.word.WordsViewModel

class ViewModelFactory(
    private val app: App
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when(modelClass) {
            LearnWordViewModel::class.java -> {
                LearnWordViewModel(Repositories.learningRepository)
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
                SettingsApplicationViewModel(Repositories.appSettingsManager.getApplicationSettings())
            }
            SettingsNotificationsViewModel::class.java -> {
                SettingsNotificationsViewModel(Repositories.appSettingsManager.getNotificationSettings())
            }
            AccountEditViewModel::class.java -> {
                AccountEditViewModel(Repositories.accountsRepository)
            }
            SettingsRepeatViewModel::class.java -> {
                SettingsRepeatViewModel(Repositories.appSettingsManager.getRepeatSettings())
            }
            SettingsLearnViewModel::class.java -> {
                SettingsLearnViewModel(Repositories.appSettingsManager.getLearnSettings())
            }
            RepeatWordViewModel::class.java -> {
                RepeatWordViewModel(Repositories.repeatingRepository)
            }
            RepeatingWordsViewModel::class.java -> {
                RepeatingWordsViewModel(Repositories.repeatingRepository)
            }
            StatisticDiagramViewModel::class.java -> {
                StatisticDiagramViewModel(Repositories.statisticRepository)
            }
            StatisticViewModel::class.java -> {
                StatisticViewModel(Repositories.achievementRepository)
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModelClass")
            }
        }
        return viewModel as T
    }
}

fun Fragment.factory() = ViewModelFactory(requireContext().applicationContext as App)