package com.poc.protodatastore.factory

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.more.proto.Preferences
import com.more.proto.Session
import com.poc.protodatastore.serializer.PreferencesSerializer
import com.poc.protodatastore.serializer.SessionSerializer

val Context.sessionDataStore: DataStore<Session> by dataStore(
    fileName = "session.pb",
    serializer = SessionSerializer
)

val Context.preferencesDataStore: DataStore<Preferences> by dataStore(
    fileName = "preferences.pb",
    serializer = PreferencesSerializer
)