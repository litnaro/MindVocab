package com.example.mindvocab.model.settings

import com.example.mindvocab.model.settings.account.AccountSettings
import com.example.mindvocab.model.settings.application.ApplicationSettings
import com.example.mindvocab.model.settings.learn.LearnSettings
import com.example.mindvocab.model.settings.notifications.NotificationSettings
import com.example.mindvocab.model.settings.repeat.RepeatSettings

class SharedPreferencesAppSettingsManager(
    private val accountSettings: AccountSettings,
    private val applicationSettings: ApplicationSettings,
    private val notificationSettings: NotificationSettings,
    private val learnSettings: LearnSettings,
    private val repeatSettings: RepeatSettings
): AppSettingsManager {

    override fun getAccountSettings() = accountSettings

    override fun getApplicationSettings() = applicationSettings

    override fun getNotificationSettings() = notificationSettings

    override fun getLearnSettings() = learnSettings

    override fun getRepeatSettings() = repeatSettings

}