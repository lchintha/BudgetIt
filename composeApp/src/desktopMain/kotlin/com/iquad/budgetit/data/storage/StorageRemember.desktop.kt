package com.iquad.budgetit.data.storage

import androidx.compose.runtime.Composable
import com.iquad.budgetit.data.storage.Storage

/**
 * This file provides a common interface for creating platform-specific Storage instances
 * in a Compose-friendly way. The actual implementations are provided in the platform-specific
 * source sets.
 */
@Composable
actual fun rememberStorage(): Storage {
    TODO("Not yet implemented")
}