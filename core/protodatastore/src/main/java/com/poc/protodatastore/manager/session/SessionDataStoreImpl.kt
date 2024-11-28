package com.poc.protodatastore.manager.session

import androidx.datastore.core.DataStore
import com.more.proto.Session
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class SessionDataStoreImpl(private val sessionDataStore: DataStore<Session>) : SessionDataStore {

    override suspend fun setAccessToken(accessToken: String) {
        sessionDataStore.updateData { currentSession ->
            currentSession.toBuilder().setAccessToken(accessToken).build()
        }
    }

    override suspend fun setRefreshToken(refreshToken: String) {
        sessionDataStore.updateData { currentSession ->
            currentSession.toBuilder().setRefreshToken(refreshToken).build()
        }
    }

    override suspend fun setUserIdToken(userId: String) {
        sessionDataStore.updateData { currentSession ->
            currentSession.toBuilder().setRefreshToken(userId).build()
        }
    }

    override suspend fun setSession(accessToken: String, refreshToken: String, userId: String) {
        sessionDataStore.updateData { currentSession ->
            currentSession.toBuilder()
                .setAccessToken(accessToken)
                .setRefreshToken(refreshToken)
                .setUserId(userId)
                .build()
        }
    }

    override suspend fun getAccessToken(): String {
        return sessionDataStore.data.first().accessToken
    }

    override fun getAccessTokenFlow(): Flow<String> {
        return sessionDataStore.data.map { currentSession ->
            currentSession.accessToken
        }
    }

    override suspend fun getRefreshToken(): String {
        return sessionDataStore.data.first().refreshToken
    }

    override fun getRefreshTokenFlow(): Flow<String> {
        return sessionDataStore.data.map { currentSession ->
            currentSession.refreshToken
        }
    }

    override suspend fun getUserId(): String {
        return sessionDataStore.data.first().userId
    }

    override fun getUserIdFlow(): Flow<String> {
        return sessionDataStore.data.map { currentSession ->
            currentSession.userId
        }
    }

    /*
getSession(): Session: This function returns a single instance of Session synchronously using the first() operator.
 It waits for the first emitted value from the sessionDataStore.data flow and returns it immediately.
 This is useful when you want to retrieve the current session data and use it immediately in your code.
    */
    override suspend fun getSession(): Session {
        return sessionDataStore.data.first()
    }

    /*
 getSessions(): Flow<Session>: This function returns a flow of Session instances.
  It doesn't retrieve the session data immediately but instead provides a stream of session data over time.
  This is useful when you want to observe changes to the session data continuously.
  You can collect or observe this flow in your code and react to any changes to the session data as they occur.
 */
    override fun getSessionFlow(): Flow<Session> {
        return sessionDataStore.data.map { currentSession ->
            currentSession
        }
    }
}