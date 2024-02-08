package com.example.mindvocab.screens.settings.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.model.settings.notifications.NotificationSettings
import kotlinx.coroutines.launch

class SettingsNotificationsViewModel(
    private val notificationSettings: NotificationSettings
) : BaseViewModel() {

    private val _isNotificationsEnabled = MutableLiveData<Boolean>()
    val isNotificationsEnabled: LiveData<Boolean> get() = _isNotificationsEnabled

    private val _isReminderEnabled = MutableLiveData<Boolean>()
    val isReminderEnabled: LiveData<Boolean> get() = _isReminderEnabled

    init {
        viewModelScope.launch {
            _isNotificationsEnabled.value = notificationSettings.getIsNotificationsEnabled()
            _isReminderEnabled.value = notificationSettings.getIsReminderEnabled()
        }
    }

    fun setReminder(enabled: Boolean) {

    }

    fun setNotifications(enabled: Boolean) {

    }

}