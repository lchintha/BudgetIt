package com.iquad.budgetit.storage

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager private constructor(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )

    companion object {
        private const val PREFS_NAME = "AppPreferences"
        private const val KEY_AMOUNT = "saved_amount"
        private const val KEY_CURRENCY = "saved_currency"
        private const val KEY_FIRST_LAUNCH = "is_first_launch"

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

    // Save amount
    fun saveAmount(amount: Double) {
        sharedPreferences.edit().apply {
            putFloat(KEY_AMOUNT, amount.toFloat())
            apply()
        }
    }

    // Get amount with default value
    fun getAmount(defaultValue: Double = 0.0): Double {
        return sharedPreferences.getFloat(KEY_AMOUNT, defaultValue.toFloat()).toDouble()
    }

    // Save currency
    fun saveCurrency(currency: String) {
        sharedPreferences.edit().apply {
            putString(KEY_CURRENCY, currency)
            apply()
        }
    }

    // Get currency with default value
    fun getCurrency(defaultValue: String = "USD"): String {
        return sharedPreferences.getString(KEY_CURRENCY, defaultValue) ?: defaultValue
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

    // Clear all preferences
    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}