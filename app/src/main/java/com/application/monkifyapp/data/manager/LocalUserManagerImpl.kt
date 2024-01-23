package com.application.monkifyapp.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.application.monkifyapp.domain.manager.LocalUserManager
import com.application.monkifyapp.util.Constants
import com.application.monkifyapp.util.Constants.USER_SETTINGS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalUserManagerImpl(
    private val context:Context
) : LocalUserManager {

    override suspend fun saveAppEntry() {
       context.dataStore.edit { settings->
       settings[PrefrencesKeys.APP_ENTRY] = true

       }
    }

    override fun readAppEntry(): Flow<Boolean> {
        return context.dataStore.data.map {preferences->
            preferences[PrefrencesKeys.APP_ENTRY]?:false
        }
    }

    override suspend fun saveDaysCompleted(daysCompleted: Int) {
        context.dataStore.edit { settings->
            settings[PrefrencesKeys.DAYS_COMPLETED] = daysCompleted

        }
    }

    override fun readDaysCompleted(): Flow<Int?> {
        return context.dataStore.data.map { preferences ->
            preferences[PrefrencesKeys.DAYS_COMPLETED]
        }
    }
}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_SETTINGS)

private object PrefrencesKeys{
    val APP_ENTRY= booleanPreferencesKey(name=Constants.APP_ENTRY)
    val DAYS_COMPLETED= intPreferencesKey(name=Constants.DAYS_COMPLETED)
}