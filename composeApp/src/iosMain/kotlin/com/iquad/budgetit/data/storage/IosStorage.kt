package com.iquad.budgetit.data.storage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.Foundation.NSUserDefaults

/**
 * This is the iOS-specific implementation of the Storage interface.
 * It uses NSUserDefaults to persist data on iOS devices.
 */
actual class Storage {
    private val userDefaults = NSUserDefaults.standardUserDefaults

    actual suspend fun putDouble(key: String, value: Double) {
        userDefaults.setDouble(value, key)
    }

    actual suspend fun getDouble(key: String, defaultValue: Double): Double {
        return if (userDefaults.objectForKey(key) != null) {
            userDefaults.doubleForKey(key)
        } else {
            defaultValue
        }
    }

    actual suspend fun putString(key: String, value: String) {
        userDefaults.setDouble(value, key)
    }

    actual suspend fun getString(key: String, defaultValue: String): String {
        return if (userDefaults.objectForKey(key) != null) {
            userDefaults.stringForKey(key)
        } else {
            defaultValue
        }
    }
}

/**
 * Platform-specific composable to create Storage instance
 */
@Composable
actual fun rememberStorage(): Storage {
    return remember { Storage() }
}