package com.example.mindvocab.screens.settings.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.model.settings.notifications.NotificationSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsNotificationsViewModel @Inject constructor(
    private val notificationSettings: NotificationSettings
) : BaseViewModel() {

    private val _isNotificationsEnabledLiveData = MutableLiveData<Boolean>()
    val isNotificationsEnabledLiveData: LiveData<Boolean> get() = _isNotificationsEnabledLiveData

    private val _isReminderEnabledLiveData = MutableLiveData<Boolean>()
    val isReminderEnabledLiveData: LiveData<Boolean> get() = _isReminderEnabledLiveData

    init {
        getNotifications()
        getReminder()
    }

    private fun getNotifications() {
        viewModelScope.launch {
            notificationSettings.isNotificationsEnabledSetting.collect {
                _isNotificationsEnabledLiveData.value = it
            }
        }
    }

    fun setNotifications(setting: Boolean) {
        viewModelScope.launch {
            notificationSettings.setIsNotificationsEnabled(setting)
        }
    }

    private fun getReminder() {
        viewModelScope.launch {
            notificationSettings.isReminderEnabledSetting.collect {
                _isReminderEnabledLiveData.value = it
            }
        }
    }

    fun setReminder(setting: Boolean) {
        viewModelScope.launch {
            notificationSettings.setIsReminderEnabled(setting)
        }
    }

}