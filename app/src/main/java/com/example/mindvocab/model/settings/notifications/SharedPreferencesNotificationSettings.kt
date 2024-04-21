package com.example.mindvocab.model.settings.notifications

import android.content.Context
import com.example.mindvocab.model.settings.AppSettings
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class SharedPreferencesNotificationSettings @Inject constructor(
    @ApplicationContext appContext: Context
) : NotificationSettings, AppSettings(appContext) {

    override val isNotificationsEnabledSetting = MutableStateFlow(getIsNotificationsEnabled())

    override val isReminderEnabledSetting = MutableStateFlow(getIsReminderEnabled())

    // General notifications

    override suspend fun setIsNotificationsEnabled(setting: Boolean) {
        if (getIsNotificationsEnabled() == setting) return
        sharedPreferences.edit()
            .putBoolean(PREF_CURRENT_NOTIFICATION_SETTING, setting)
            .apply()
        isNotificationsEnabledSetting.emit(setting)
    }

    private fun getIsNotificationsEnabled(): Boolean {
        return sharedPreferences.getBoolean(PREF_CURRENT_NOTIFICATION_SETTING, false)
    }

    // Reminder

    override suspend fun setIsReminderEnabled(setting: Boolean) {
        if (getIsReminderEnabled() == setting) return
        sharedPreferences.edit()
            .putBoolean(PREF_CURRENT_REMINDER_SETTING, setting)
            .apply()
        isReminderEnabledSetting.emit(setting)
    }

    private fun getIsReminderEnabled(): Boolean {
        return sharedPreferences.getBoolean(PREF_CURRENT_REMINDER_SETTING, true)
    }

    companion object {
        private const val PREF_CURRENT_NOTIFICATION_SETTING = "currentNotificationSettings"
        private const val PREF_CURRENT_REMINDER_SETTING = "currentReminderSettings"
    }
}