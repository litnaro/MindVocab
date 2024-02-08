package com.example.mindvocab.model.settings.notifications

import android.content.Context

class SharedPreferencesNotificationSettings(
    appContext: Context
) : NotificationSettings {

    private val sharedPreferences = appContext.getSharedPreferences("settings", Context.MODE_PRIVATE)

    override fun getIsNotificationsEnabled(): Boolean {
        return sharedPreferences.getBoolean(PREF_CURRENT_NOTIFICATION_SETTING, false)
    }

    override fun setIsNotificationsEnabled(enabled: Boolean) {
        sharedPreferences.edit()
            .putBoolean(PREF_CURRENT_NOTIFICATION_SETTING, enabled)
            .apply()
    }

    override fun getIsReminderEnabled(): Boolean {
        return sharedPreferences.getBoolean(PREF_CURRENT_REMINDER_SETTING, true)
    }

    override fun setIsReminderEnabled(enabled: Boolean) {
        sharedPreferences.edit()
            .putBoolean(PREF_CURRENT_REMINDER_SETTING, enabled)
            .apply()
    }

    companion object {
        private const val PREF_CURRENT_NOTIFICATION_SETTING = "currentNotificationSettings"
        private const val PREF_CURRENT_REMINDER_SETTING = "currentReminderSettings"
    }
}