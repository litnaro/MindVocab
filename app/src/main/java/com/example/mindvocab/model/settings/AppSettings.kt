package com.example.mindvocab.model.settings

import com.example.mindvocab.model.settings.account.AccountSettings
import com.example.mindvocab.model.settings.application.ApplicationSettings
import com.example.mindvocab.model.settings.notifications.NotificationSettings
import com.example.mindvocab.model.settings.repeat.RepeatSettings

interface AppSettings {

    fun getAccountSettings() : AccountSettings

    fun getApplicationSettings() : ApplicationSettings

    fun getNotificationSettings() : NotificationSettings

    fun getRepeatSettings() : RepeatSettings

}
