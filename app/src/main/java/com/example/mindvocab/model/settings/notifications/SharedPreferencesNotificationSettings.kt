package com.example.mindvocab.model.settings.notifications

import android.content.Context
import com.example.mindvocab.model.settings.AppSettings
import kotlinx.coroutines.flow.MutableStateFlow

class SharedPreferencesNotificationSettings(
    appContext: Context
) : NotificationSettings, AppSettings(appContext) {

    // General notifications

    override val isNotificationsEnabledSetting = MutableStateFlow(getIsNotificationsEnabled())

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

    override val isReminderEnabledSetting = MutableStateFlow(getIsReminderEnabled())

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