package com.example.mindvocab.model.settings

import com.example.mindvocab.model.settings.account.AccountSettings
import com.example.mindvocab.model.settings.application.ApplicationSettings
import com.example.mindvocab.model.settings.notifications.NotificationSettings

class SharedPreferencesAppSettings(
    private val accountSettings: AccountSettings,
    private val applicationSettings: ApplicationSettings,
    private val notificationSettings: NotificationSettings
): AppSettings {

    override fun getAccountSettings() = accountSettings

    override fun getApplicationSettings() = applicationSettings

    override fun getNotificationSettings() = notificationSettings

}