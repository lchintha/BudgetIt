package com.iquad.budgetit.data.storage

/**
 * This is the Android-specific implementation of the Storage interface using Jetpack DataStore.
 * It provides an asynchronous, consistent, and transactional way to store key-value pairs.
 */

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

// Extension property for Context to create a single DataStore instance
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "budget_it_preferences")

actual class Storage(private val context: Context) {

    /**
     * Stores a double value with the given key using DataStore
     * Note: This is a blocking call wrapped in runBlocking because the interface
     * is synchronous. In a real app, consider making the interface suspend.
     */
    actual suspend fun putDouble(key: String, value: Double) {
        runBlocking {
            context.dataStore.edit { preferences ->
                preferences[doublePreferencesKey(key)] = value
            }
        }
    }

    /**
     * Retrieves a double value for the given key from DataStore
     * Note: This is a blocking call wrapped in runBlocking because the interface
     * is synchronous. In a real app, consider making the interface suspend.
     */
    actual suspend fun getDouble(key: String, defaultValue: Double): Double {
        return runBlocking {
            context.dataStore.data.first()[doublePreferencesKey(key)] ?: defaultValue
        }
    }

    /**
     * Stores a string value with the given key using DataStore
     * Note: This is a blocking call wrapped in runBlocking because the interface
     * is synchronous. In a real app, consider making the interface suspend.
     */
    actual suspend fun putString(key: String, value: String) {
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = value
        }
    }

    /**
     * Retrieves a string value with the given key using DataStore
     * Note: This is a blocking call wrapped in runBlocking because the interface
     * is synchronous. In a real app, consider making the interface suspend.
     */
    actual suspend fun getString(key: String, defaultValue: String): String {
        return context.dataStore.data.first()[stringPreferencesKey(key)] ?: defaultValue
    }

    companion object {
        /**
         * Creates preference keys for DataStore
         */
        private fun prefKey(key: String) = doublePreferencesKey(key)
    }
}

/**
 * Platform-specific composable to create Storage instance
 */
@Composable
actual fun rememberStorage(): Storage {
    val context = LocalContext.current
    return remember { Storage(context) }
}