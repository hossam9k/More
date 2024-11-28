package com.poc.protodatastore.manager.preferences

import androidx.datastore.core.DataStore
import com.more.proto.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class PreferencesDataStoreImpl(
    private val preferencesDataStore: DataStore<Preferences>,
) : PreferencesDataStore {

    override suspend fun setLanguage(language: String) {
        preferencesDataStore.updateData {
            it.toBuilder().setLanguage(language).build()
        }
    }

    override suspend fun setIsAppLockEnabled(isAppLockedEnabled: Boolean) {
        preferencesDataStore.updateData {
            it.toBuilder().setIsAppLockEnabled(isAppLockedEnabled).build()
        }
    }

    override suspend fun setNotificationCount(notificationCount: Int) {
        preferencesDataStore.updateData {
            it.toBuilder().setNotificationCount(notificationCount).build()
        }
    }

    override suspend fun setMoneyBalance(moneyBalance: Long) {
        preferencesDataStore.updateData {
            it.toBuilder().setMoneyBalance(moneyBalance).build()
        }
    }

    override suspend fun getLanguage(): String {
        return preferencesDataStore.data.first().language
    }

    override fun getLanguageFlow(): Flow<String> {
        return preferencesDataStore.data.map {
            it.language
        }
    }

    override suspend fun isAppLockEnabled(): Boolean {
        return preferencesDataStore.data.first().isAppLockEnabled
    }

    override fun isAppLockEnabledFlow(): Flow<Boolean> {
        return preferencesDataStore.data.map {
            it.isAppLockEnabled
        }
    }

    override suspend fun getNotificationCount(): Int {
        return preferencesDataStore.data.first().notificationCount
    }

    override fun getNotificationCountFlow(): Flow<Int> {
        return preferencesDataStore.data.map {
            it.notificationCount
        }
    }

    override suspend fun getMoneyBalance(): Long {
        return preferencesDataStore.data.first().moneyBalance
    }

    override fun getMoneyBalanceFlow(): Flow<Long> {
        return preferencesDataStore.data.map {
            it.moneyBalance
        }
    }
}