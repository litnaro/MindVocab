package com.example.mindvocab.model.settings.notifications

interface NotificationSettings {

    fun getIsNotificationsEnabled() : Boolean

    fun setIsNotificationsEnabled(enabled: Boolean)

    fun getIsReminderEnabled() : Boolean

    fun setIsReminderEnabled(enabled: Boolean)

}