package com.example.mindvocab.model.settings.notifications

import kotlinx.coroutines.flow.MutableStateFlow

interface NotificationSettings {

    val isNotificationsEnabledSetting: MutableStateFlow<Boolean>

    suspend fun setIsNotificationsEnabled(setting: Boolean)

    val isReminderEnabledSetting: MutableStateFlow<Boolean>

    suspend fun setIsReminderEnabled(setting: Boolean)

}