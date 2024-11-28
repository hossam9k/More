package com.poc.protodatastore.manager.preferences

import kotlinx.coroutines.flow.Flow

interface PreferencesDataStore {

    //Setter Functions

    suspend fun setLanguage(language: String)

    suspend fun setIsAppLockEnabled(isAppLockedEnabled: Boolean)

    suspend fun setNotificationCount(notificationCount: Int)

    suspend fun setMoneyBalance(moneyBalance: Long)

    //Getter Functions

    suspend fun getLanguage(): String

    fun getLanguageFlow(): Flow<String>

    suspend fun isAppLockEnabled(): Boolean

    fun isAppLockEnabledFlow(): Flow<Boolean>

    suspend fun getNotificationCount(): Int

    fun getNotificationCountFlow(): Flow<Int>

    suspend fun getMoneyBalance(): Long

    fun getMoneyBalanceFlow(): Flow<Long>
}