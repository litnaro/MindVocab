package com.example.mindvocab.di

import com.example.mindvocab.model.settings.account.AccountSettings
import com.example.mindvocab.model.settings.account.SharedPreferencesAccountSettings
import com.example.mindvocab.model.settings.application.ApplicationSettings
import com.example.mindvocab.model.settings.application.SharedPreferencesApplicationSettings
import com.example.mindvocab.model.settings.learn.LearningSettings
import com.example.mindvocab.model.settings.learn.SharedPreferencesLearningSettings
import com.example.mindvocab.model.settings.notifications.NotificationSettings
import com.example.mindvocab.model.settings.notifications.SharedPreferencesNotificationSettings
import com.example.mindvocab.model.settings.repeat.RepeatSettings
import com.example.mindvocab.model.settings.repeat.SharedPreferencesRepeatSettings
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsModule {

    @Binds
    @Singleton
    abstract fun bindApplicationSettings(
        applicationSettings: SharedPreferencesApplicationSettings
    ) : ApplicationSettings

    @Binds
    @Singleton
    abstract fun bindAccountSettings(
        accountSetting: SharedPreferencesAccountSettings
    ) : AccountSettings

    @Binds
    @Singleton
    abstract fun bindNotificationSettings(
        notificationSettings: SharedPreferencesNotificationSettings
    ) : NotificationSettings

    @Binds
    @Singleton
    abstract fun bindLearningSettings(
        learningSettings: SharedPreferencesLearningSettings
    ) : LearningSettings

    @Binds
    @Singleton
    abstract fun bindRepeatingSettings(
        repeatSettings: SharedPreferencesRepeatSettings
    ) : RepeatSettings

}