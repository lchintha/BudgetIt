package com.iquad.budgetit.storage

import android.content.Context
import android.content.SharedPreferences
import com.iquad.budgetit.model.ThemeMode

class PreferencesManager private constructor(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )

    companion object {
        private const val PREFS_NAME = "AppPreferences"
        private const val KEY_FIRST_LAUNCH = "is_first_launch"
        private const val KEY_APPEARANCE = "appearance"

        @Volatile
        private var instance: PreferencesManager? = null

        fun getInstance(context: Context): PreferencesManager {
            return instance ?: synchronized(this) {
                instance ?: PreferencesManager(context.applicationContext).also {
                    instance = it
                }
            }
        }
    }

    // Save first launch flag
    fun setFirstLaunch(isFirstLaunch: Boolean) {
        sharedPreferences.edit().apply {
            putBoolean(KEY_FIRST_LAUNCH, isFirstLaunch)
            apply()
        }
    }

    // Check if it's first launch
    fun isFirstLaunch(): Boolean {
        return sharedPreferences.getBoolean(KEY_FIRST_LAUNCH, true)
    }

    // Save appearance option
    fun setAppearance(themeMode: ThemeMode) {
        sharedPreferences.edit().apply {
            putString(KEY_APPEARANCE, themeMode.name)
            apply()
        }
    }

    // Get saved appearance option
    fun getAppearance(): String {
        return sharedPreferences.getString(KEY_APPEARANCE, ThemeMode.System.name)
            ?: ThemeMode.Light.name
    }
}