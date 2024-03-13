package com.example.mindvocab.model.settings.account

import android.content.Context
import com.example.mindvocab.model.settings.AppSettings
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferencesAccountSettings @Inject constructor(
    @ApplicationContext appContext: Context
) : AccountSettings, AppSettings(appContext) {

    override suspend fun getCurrentAccountId(): Long = sharedPreferences.getLong(PREF_CURRENT_ACCOUNT_ID,
        AccountSettings.NO_ACCOUNT_ID
    )

    override suspend fun setCurrentAccountId(accountId: Long) {
        sharedPreferences.edit()
            .putLong(PREF_CURRENT_ACCOUNT_ID, accountId)
            .apply()
    }

    companion object {
        private const val PREF_CURRENT_ACCOUNT_ID = "currentAccountId"
    }
}